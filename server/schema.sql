-- Users Table
CREATE TABLE IF NOT EXISTS users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    email TEXT UNIQUE NOT NULL,
    password TEXT NOT NULL
);

-- Categories Table
CREATE TABLE IF NOT EXISTS categories (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    type TEXT NOT NULL,
    isSystem BOOLEAN DEFAULT 0,
    own INTEGER, -- This stores the user ID
    FOREIGN KEY (own) REFERENCES users(id) ON DELETE CASCADE
);

-- Expenses Table
CREATE TABLE IF NOT EXISTS expenses (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    userId INTEGER NOT NULL,
    categoryId INTEGER NOT NULL,
    amount REAL NOT NULL CHECK (amount >= 0),  -- Ensure amount is non-negative
    isIncome BOOLEAN DEFAULT 0,
    date TEXT NOT NULL DEFAULT (strftime('%Y-%m-%d %H:%M:%S', 'now')),
    description TEXT DEFAULT '',  -- Default empty description
    receiptUrl TEXT DEFAULT NULL,  -- Optional field for storing file paths

    FOREIGN KEY (userId) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (categoryId) REFERENCES categories(id) ON DELETE CASCADE
);

-- Insert sample categories
INSERT INTO categories (name, type, isSystem, own) VALUES
    ('Food', 'food', 1, NULL),
    ('Travel', 'transport', 1, NULL),
    ('Utilities', 'bills', 1, NULL),
    ('Entertainment', 'leisure', 1, NULL),
    ('Healthcare', 'medical', 1, NULL),
    ('Education', 'learning', 1, NULL),
    ('Shopping', 'retail', 1, NULL),
    ('Insurance', 'financial', 1, NULL),
    ('Housing', 'rent', 1, NULL),
    ('Savings', 'investment', 1, NULL);