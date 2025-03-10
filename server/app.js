const express = require("express");
const jwt = require("jsonwebtoken");
const bcrypt = require("bcryptjs");
const sqlite3 = require("sqlite3").verbose();
const path = require('path');
const multer = require("multer");
const baseResponse = require("./utils/baseResponse");
require("dotenv").config();

const SECRET_KEY = process.env.SECRET_KEY || "mydefaultsecret";
const app = express();
app.use('/uploads', express.static(path.join(__dirname, 'uploads')));
app.use(express.json());

const storage = multer.diskStorage({
  destination: (req, file, cb) => cb(null, "uploads/"),
  filename: (req, file, cb) => cb(null, Date.now() + "-" + file.originalname),
});
const upload = multer({ storage });

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



//  █████╗ ██╗   ██╗████████╗██╗  ██╗███████╗███╗  ██╗   █████╗ ██████╗ ██╗
// ██╔══██╗██║   ██║╚══██╔══╝██║  ██║██╔════╝████╗ ██║  ██╔══██╗██╔══██╗██║
// ███████║██║   ██║   ██║   ███████║█████╗  ██╔██╗██║  ███████║██████╔╝██║
// ██╔══██║██║   ██║   ██║   ██╔══██║██╔══╝  ██║╚████║  ██╔══██║██╔═══╝ ██║
// ██║  ██║╚██████╔╝   ██║   ██║  ██║███████╗██║ ╚███║  ██║  ██║██║     ██║
// ╚═╝  ╚═╝ ╚═════╝    ╚═╝   ╚═╝  ╚═╝╚══════╝╚═╝  ╚══╝  ╚═╝  ╚═╝╚═╝     ╚═╝

// **Sign-Up Route**
app.post("/signup", (req, res) => {
  console.log("SIGN UP -->", req.body)
  const { name, email, password } = req.body;

  if (!name || !email || !password) {
    return res.status(200).json(baseResponse("error", "All fields are required"));
  }

  // Check if user exists
  db.get("SELECT * FROM users WHERE email = ?", [email], async (err, existingUser) => {
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
    const token = jwt.sign({ id: user.id, email: user.email }, SECRET_KEY, { expiresIn: "24h" });

    res.json(baseResponse("success", "Login successful", { user: { id: user.id, email: user.email, name: user.name }, token }));
  });
});

// **Profile Route**
app.get("/profile", authenticateToken, (req, res) => {
  const userId = req.user.id;
  console.log("PROFILE  -->", userId)

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
  const email = req.user.email;
  console.log("LOGOUT -->", email)
    const authHeader = req.headers["authorization"];
    const token = authHeader && authHeader.split(" ")[1];

    if (token) {
        BLACKLISTED_TOKENS.add(token); // Add token to blacklist
    }

    res.json(baseResponse("success", "Logged out successfully"));
});

// ███████╗██╗  ██╗██████╗ ███████╗███╗  ██╗ ██████╗███████╗   █████╗ ██████╗ ██╗
// ██╔════╝╚██╗██╔╝██╔══██╗██╔════╝████╗ ██║██╔════╝██╔════╝  ██╔══██╗██╔══██╗██║
// █████╗   ╚███╔╝ ██████╔╝█████╗  ██╔██╗██║╚█████╗ █████╗    ███████║██████╔╝██║
// ██╔══╝   ██╔██╗ ██╔═══╝ ██╔══╝  ██║╚████║ ╚═══██╗██╔══╝    ██╔══██║██╔═══╝ ██║
// ███████╗██╔╝╚██╗██║     ███████╗██║ ╚███║██████╔╝███████╗  ██║  ██║██║     ██║
// ╚══════╝╚═╝  ╚═╝╚═╝     ╚══════╝╚═╝  ╚══╝╚═════╝ ╚══════╝  ╚═╝  ╚═╝╚═╝     ╚═╝

// **1. Create Expense**
app.post("/expenses", authenticateToken, upload.array("invoices"), (req, res) => {
  const userId = req.user.id;
  console.log("Create EXPENSE  -->", userId, req.body);
  const { categoryId, amount, description, date, categoryName, isIncome } = req.body;
  const invoiceFiles = req.files.map(file => file.path).join("|"); // File paths

  const sql = `INSERT INTO expenses (userId, categoryId, amount, description, receiptUrl, date, isIncome) VALUES (?, ?, ?, ?, ?, ?, ?)`;
  db.run(sql, [userId, categoryId, amount, description, invoiceFiles, date, isIncome], function (err) {
    if (err) {
      return res.status(200).json(baseResponse('error', "Failed to create expense", null, err.message));
    }
    res.json(baseResponse('success', "Expense added successfully", { id: this.lastID, categoryId, categoryName, amount, isIncome, date, description, receiptUrl: invoiceFiles }));
  });
});

// **2. Read All Expenses**
app.get("/expenses", authenticateToken, (req, res) => {
  const userId = req.user.id;
  console.log("GET ALL EXPENSE  -->", userId);

  db.all("SELECT e.id, e.categoryId, c.name AS categoryName, e.amount, e.isIncome, e.date, e.description FROM expenses e JOIN categories c ON e.categoryId = c.id WHERE userId = ?", [userId], (err, rows) => {
    if (err) {
      return res.status(200).json(baseResponse('error', "Failed to fetch expenses", null, err.message));
    }
    res.json(baseResponse('success', "Expenses retrieved successfully", rows));
  });
});

// **3. Read Single Expense**
app.get("/expenses/:id", authenticateToken, (req, res) => {
  const userId = req.user.id;
  const expenseId = req.params.id;
  console.log("GET EXPENSE by ID  -->", expenseId);

  db.get("SELECT e.id, e.categoryId, c.name AS categoryName, e.amount, e.isIncome, e.date, e.description, e.receiptUrl FROM expenses e JOIN categories c ON e.categoryId = c.id WHERE e.id = ? AND userId = ?", [expenseId, userId], (err, row) => {
    if (err) {
      return res.status(200).json(baseResponse('error', "Failed to fetch expense", null, err.message));
    }
    if (!row) {
      return res.status(200).json(baseResponse('error', "Expense not found"));
    }
    res.json(baseResponse('success', "Expense retrieved successfully", row));
  });
});

// **4. Update Expense**
app.put("/expenses/:id", authenticateToken, upload.array("invoices"), (req, res) => {
  const userId = req.user.id;
  const id = req.params.id;
  const { categoryId, amount, date, description } = req.body;
  const invoiceFiles = req.files.map(file => file.path); // File paths
  console.log("UPDATE EXPENSE by ID  -->", id);

  const sql = `UPDATE expenses SET categoryId = ?, amount = ?, date = ?, description = ?, receiptUrl = ? WHERE id = ? AND userId = ?`;

  db.run(sql, [categoryId, amount, date, description, invoiceFiles.join("|"), id, userId], function (err) {
    if (err) {
      return res.status(200).json(baseResponse('error', "Failed to update expense", null, err.message));
    }
    if (this.changes === 0) {
      return res.status(200).json(baseResponse('error', "Expense not found or no permission to update"));
    }
    res.json(baseResponse('success', "Expense updated successfully"));
  });
});

// **5. Delete Expense**
app.delete("/expenses/:id", authenticateToken, (req, res) => {
  const userId = req.user.id;
  const id = req.params.id;
  console.log("DELETE EXPENSE by ID  -->", id);

  db.run("DELETE FROM expenses WHERE id = ? AND userId = ?", [id, userId], function (err) {
    if (err) {
      return res.status(200).json(baseResponse('error', "Failed to delete expense", null, err.message));
    }
    if (this.changes === 0) {
      return res.status(200).json(baseResponse('error', "Expense not found or no permission to delete"));
    }
    res.json(baseResponse('success', "Expense deleted successfully"));
  });
});



//  █████╗  █████╗ ████████╗███████╗ ██████╗  █████╗ ██████╗ ██╗███████╗ ██████╗
// ██╔══██╗██╔══██╗╚══██╔══╝██╔════╝██╔════╝ ██╔══██╗██╔══██╗██║██╔════╝██╔════╝
// ██║  ╚═╝███████║   ██║   █████╗  ██║  ██╗ ██║  ██║██████╔╝██║█████╗  ╚█████╗ 
// ██║  ██╗██╔══██║   ██║   ██╔══╝  ██║  ╚██╗██║  ██║██╔══██╗██║██╔══╝   ╚═══██╗
// ╚█████╔╝██║  ██║   ██║   ███████╗╚██████╔╝╚█████╔╝██║  ██║██║███████╗██████╔╝
//  ╚════╝ ╚═╝  ╚═╝   ╚═╝   ╚══════╝ ╚═════╝  ╚════╝ ╚═╝  ╚═╝╚═╝╚══════╝╚═════╝ 

// Create a new category (POST)
app.post('/categories', authenticateToken, (req, res) => {
  const { name, type } = req.body;
  const own = req.user.id; // User ID from JWT

  if (!name || !type) return res.status(200).json(baseResponse('error', 'Name and type are required'));

  db.run("INSERT INTO categories (name, type, isSystem, own) VALUES (?, ?, 0, ?)", [name, type, own], function (err) {
      if (err) return res.status(200).json(baseResponse('error', err.message));

      res.status(200).json(baseResponse('success', "create success", { id: this.lastID, name, type, isSystem: 0, own }));
  });
});

// Get all categories (GET)
app.get('/categories', authenticateToken, (req, res) => {
  const own = req.user.id; // User ID from JWT
  console.log("Get All Categories  -->", own, req.body);

  db.all("SELECT * FROM categories WHERE isSystem = 1 OR own = ?", [own], (err, rows) => {
      if (err) return res.status(200).json(baseResponse('error', err.message));

      res.json(baseResponse('success', 'get all categories success', rows));
  });
});

// Get categories for the authenticated user (GET)
app.get('/categories/user', authenticateToken, (req, res) => {
  db.all("SELECT * FROM categories WHERE own = ? OR isSystem = 1", [req.user.id], (err, rows) => {
      if (err) return res.status(200).json('error', err.message);

      res.json(baseResponse('success', "success", rows));
  });
});

// Get system categories only (GET)
app.get('/categories/system', authenticateToken, (req, res) => {
  db.all("SELECT * FROM categories WHERE isSystem = 1", [], (err, rows) => {
      if (err) return res.status(200).json('error', err.message);

      res.json(baseResponse('success', "success", rows));
  });
});

// Get a single category (GET)
app.get('/categories/:id', authenticateToken, (req, res) => {
  const { id } = req.params;
  db.get("SELECT * FROM categories WHERE id = ?", [id], (err, row) => {
      if (err) return res.status(200).json('error', err.message);

      if (!row) return res.status(200).json('error', 'Category not found');

      res.json(baseResponse('success', "success", row));
  });
});

// Update a category (PUT) - Only if user owns it
app.put('/categories/:id', authenticateToken, (req, res) => {
  const { id } = req.params;
  const { name, type } = req.body;
  const own = req.user.id; // Get user ID from JWT

  if (!name || !type) return res.status(200).json(baseResponse('error', 'Name and type are required'));

  db.run("UPDATE categories SET name = ?, type = ? WHERE id = ? AND own = ? AND isSystem = 0",
      [name, type, id, own], function (err) {
          if (err) return res.status(200).json('error', err.message);

          if (this.changes === 0) return res.status(200).json('error', 'Cannot update system categories or others');

          res.json(baseResponse('success', "success", { id, name, type, own }));
      });
});

// Delete a category (DELETE) - Only if user owns it
app.delete('/categories/:id', authenticateToken, (req, res) => {
  const { id } = req.params;
  const own = req.user.id; // Get user ID from JWT

  db.run("DELETE FROM categories WHERE id = ? AND own = ? AND isSystem = 0", [id, own], function (err) {
      if (err) return res.status(200).json('error', err.message);
      if (this.changes === 0) return res.status(200).json('error', 'Cannot delete system categories or others' );

      res.json(baseResponse('success', 'Category deleted successfully'));
  });
});


// **Start Server**
const PORT = process.env.PORT || 3000;
app.listen(PORT, () => console.log(`Server running on port ${PORT}`));
