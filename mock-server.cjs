// Mock Backend Server for BookStore
const express = require('express');
const cors = require('cors');

const app = express();
const PORT = 8080;

// Middleware
app.use(cors());
app.use(express.json());

// Mock Data
const users = [
  { id: 1, username: 'admin', password: 'admin123', name: 'Administrator', role: 'admin' },
  { id: 2, username: 'user1', password: 'user123', name: 'John Doe', role: 'user' }
];

const books = [
  { id: 1, title: 'JavaScript Guide', author: 'MDN', quantity: 50, import_price: 200000 },
  { id: 2, title: 'Vue.js Essentials', author: 'Vue Team', quantity: 30, import_price: 300000 }
];

// Auth Routes
app.post('/bookstore/auth/token', (req, res) => {
  const { username, password } = req.body;

  const user = users.find(u => u.username === username && u.password === password);

  if (user) {
    res.json({
      code: 1000,
      message: 'Success',
      result: {
        authenticated: true,
        token: 'mock-jwt-token-' + user.id,
        user: {
          id: user.id,
          username: user.username,
          name: user.name,
          role: user.role
        }
      }
    });
  } else {
    res.status(401).json({
      code: 1002,
      message: 'Unauthenticated',
      result: {
        authenticated: false
      }
    });
  }
});

// Books Routes
app.get('/bookstore/books', (req, res) => {
  res.json({
    code: 1000,
    message: 'Success',
    result: books
  });
});

app.post('/bookstore/books', (req, res) => {
  const newBook = {
    id: books.length + 1,
    ...req.body
  };
  books.push(newBook);

  res.json({
    code: 1000,
    message: 'Book created successfully',
    result: newBook
  });
});

// Users Routes
app.get('/bookstore/users', (req, res) => {
  res.json({
    code: 1000,
    message: 'Success',
    result: users.map(u => ({ ...u, password: undefined }))
  });
});

// Health Check
app.get('/bookstore/health', (req, res) => {
  res.json({
    status: 'OK',
    message: 'Mock Server is running',
    timestamp: new Date().toISOString()
  });
});

// Default 404
app.use('*', (req, res) => {
  res.status(404).json({
    code: 404,
    message: 'Endpoint not found',
    path: req.originalUrl
  });
});

app.listen(PORT, () => {
  console.log(`🚀 Mock BookStore API Server running on http://localhost:${PORT}`);
  console.log(`📚 Health Check: http://localhost:${PORT}/bookstore/health`);
});
