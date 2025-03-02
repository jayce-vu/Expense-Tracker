const express = require("express");
const jwt = require("jsonwebtoken");
const bcrypt = require("bcryptjs");
const sqlite3 = require("sqlite3").verbose();
const baseResponse = require("./utils/baseResponse");
require("dotenv").config();

const SECRET_KEY = process.env.SECRET_KEY || "mydefaultsecret";
const app = express();
app.use(express.json());

// Connect to SQLite Database
const db = new sqlite3.Database("./database.db", sqlite3.OPEN_READWRITE, (err) => {
  if (err) console.error("Database connection error:", err);
  else console.log("Connected to SQLite database.");
});

const BLACKLISTED_TOKENS = new Set();


// **Authentication Middleware**
const authenticateToken = (req, res, next) => {
  const token = req.headers.authorization?.split(" ")[1];

  if (!token) {
    return res.status(401).json(baseResponse("error", "Access denied. No token provided."));
  }

  // Check if token is blacklisted
  if (BLACKLISTED_TOKENS.has(token)) {
    return res.status(403).json(baseResponse("error", "Invalid token", null, "Token has been invalidated. Please log in again."));
  }

  jwt.verify(token, SECRET_KEY, (err, user) => {
    if (err) {
      return res.status(403).json(baseResponse("error", "Invalid token", null, err.message));
    }
    req.user = user;
    next();
  });
};


// **Sign-Up Route**
app.post("/signup", (req, res) => {
  console.log("SIGN UP -->", req.body)
  const { name, email, password } = req.body;

  if (!name || !email || !password) {
    return res.status(200).json(baseResponse("error", "All fields are required"));
  }

  // Check if user exists
  db.get("SELECT * FROM users WHERE email = ? OR name = ?", [email], async (err, existingUser) => {
    if (err) return res.status(200).json(baseResponse("error", "Database error", null, err.message));

    if (existingUser) {
      return res.status(200).json(baseResponse("error", "email already in use"));
    }

    // Hash password
    const hashedPassword = await bcrypt.hash(password, 10);

    // Insert user into DB
    db.run("INSERT INTO users (name, email, password) VALUES (?, ?, ?)", [name, email, hashedPassword], function (err) {
      if (err) return res.status(200).json(baseResponse("error", "Failed to register user", null, err.message));

      // Generate JWT token
      const token = jwt.sign({ id: this.lastID, name }, SECRET_KEY, { expiresIn: "1h" });

      res.json(baseResponse("success", "User registered successfully", { name, email }));
    });
  });
});

// **Login Route**
app.post("/login", (req, res) => {
  console.log("LOGIN -->", req.body)

  const { email, password } = req.body;

  if (!email || !password) {
    return res.status(200).json(baseResponse("error", "All fields are required"));
  }

  db.get("SELECT * FROM users WHERE email = ?", [email], async (err, user) => {
    if (err) return res.status(200).json(baseResponse("error", "Database error", null, err.message));

    if (!user) {
      return res.status(200).json(baseResponse("error", "Invalid email or password"));
    }

    // Verify password
    const isMatch = await bcrypt.compare(password, user.password);
    if (!isMatch) {
      return res.status(200).json(baseResponse("error", "Invalid email or password"));
    }

    // Generate JWT
    const token = jwt.sign({ id: user.id, username: user.email }, SECRET_KEY, { expiresIn: "1h" });

    res.json(baseResponse("success", "Login successful", { user: { id: user.id, email: user.email, name: user.name }, token }));
  });
});

// **Profile Route**
app.get("/profile", authenticateToken, (req, res) => {
  const userId = req.user.id;

  db.get("SELECT id, email, name FROM users WHERE id = ?", [userId], (err, user) => {
    if (err) return res.status(500).json(baseResponse("error", "Database error", null, err.message));

    if (!user) {
      return res.status(404).json(baseResponse("error", "User not found"));
    }

    res.json(baseResponse("success", "User profile fetched successfully", { user }));
  });
});

// Logout Route
app.post("/logout", authenticateToken, (req, res) => {
    const authHeader = req.headers["authorization"];
    const token = authHeader && authHeader.split(" ")[1];

    if (token) {
        BLACKLISTED_TOKENS.add(token); // Add token to blacklist
    }

    res.json(baseResponse("success", "Logged out successfully"));
});

// **Start Server**
const PORT = process.env.PORT || 3000;
app.listen(PORT, () => console.log(`Server running on port ${PORT}`));
