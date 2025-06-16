// Simple Mock Backend Server for BookStore
const express = require('express');
const cors = require('cors');
const app = express();
const PORT = 8080;

// Middleware
app.use(cors());
app.use(express.json());

// Mock Data
const users = [
  { id: 1, username: 'admin', password: 'admin123', name: 'Administrator', role: 'admin' }
];

// Auth Routes
app.post('/bookstore/auth/token', (req, res) => {
  console.log('Login attempt:', req.body);

  const { username, password } = req.body;
  const user = users.find(u => u.username === username && u.password === password);

  if (user) {
    console.log('Login successful for:', username);
    res.json({
      code: 1000,
      message: 'Success',
      result: {
        authenticated: true,
        token: 'mock-jwt-token-' + user.id,
        user: { id: user.id, username: user.username, name: user.name, role: user.role }
      }
    });
  } else {
    console.log('Login failed for:', username);
    res.status(401).json({
      code: 1002,
      message: 'Unauthenticated',
      result: { authenticated: false }
    });
  }
});

// Health Check
app.get('/bookstore/health', (req, res) => {
  res.json({ status: 'OK', message: 'Mock Server is running' });
});

// Catch all
app.use('*', (req, res) => {
  console.log('404 for:', req.originalUrl);
  res.status(404).json({ code: 404, message: 'Endpoint not found' });
});

app.listen(PORT, () => {
  console.log(`🚀 Mock BookStore API Server running on http://localhost:${PORT}`);
  console.log(`📚 Health Check: http://localhost:${PORT}/bookstore/health`);
});
