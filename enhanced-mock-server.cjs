const express = require('express');
const cors = require('cors');
const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');

const app = express();
const PORT = 8080;

// Middleware
app.use(cors({
  origin: ['http://localhost:5173', 'http://localhost:5174', 'http://localhost:5175'],
  credentials: true
}));
app.use(express.json());

// In-memory database (simulating H2)
let users = [
  {
    id: '1',
    username: 'admin',
    password: null, // Will be set during startup
    firstName: 'Admin',
    lastName: 'User',
    email: 'admin@bookstore.com',
    phone: '0123456789',
    roles: ['ADMIN']
  }
];

// Initialize admin password during startup
const initializeAdmin = async () => {
  if (users[0].password === null) {
    users[0].password = await hashPassword('admin123');
    console.log('✅ Admin password initialized');
  }
};

const books = [];
const branches = [];

// JWT Secret
const JWT_SECRET = 'YourSecretKeyHereMustBeLongEnoughForHS512Algorithm';
const JWT_EXPIRATION = '24h';

// Helper functions
const generateToken = (user) => {
  return jwt.sign(
    { username: user.username, userId: user.id },
    JWT_SECRET,
    { expiresIn: JWT_EXPIRATION }
  );
};

const validateToken = (token) => {
  try {
    return jwt.verify(token, JWT_SECRET);
  } catch (error) {
    return null;
  }
};

const hashPassword = async (password) => {
  return await bcrypt.hash(password, 10);
};

const comparePassword = async (password, hashedPassword) => {
  return await bcrypt.compare(password, hashedPassword);
};

// Middleware for authentication
const authenticateToken = (req, res, next) => {
  const authHeader = req.headers['authorization'];
  const token = authHeader && authHeader.split(' ')[1];

  if (!token) {
    return res.status(401).json({
      code: 1006,
      message: 'Unauthenticated'
    });
  }

  const decoded = validateToken(token);
  if (!decoded) {
    return res.status(401).json({
      code: 1006,
      message: 'Unauthenticated'
    });
  }

  req.user = decoded;
  next();
};

// Routes
app.get('/bookstore/health', (req, res) => {
  res.json({
    code: 1000,
    message: 'Success',
    result: {
      status: 'UP',
      timestamp: new Date().toISOString(),
      service: 'Bookstore Backend (Mock)',
      version: '1.0.0'
    }
  });
});

// Authentication endpoint
app.post('/bookstore/auth/token', async (req, res) => {
  try {
    const { username, password } = req.body;

    console.log('Login attempt:', { username, password });

    // Input validation
    if (!username || username.length < 4) {
      return res.status(400).json({
        code: 1003,
        message: 'Username must be at least 4 characters'
      });
    }

    if (!password || password.length < 8) {
      return res.status(400).json({
        code: 1004,
        message: 'Password must be at least 8 characters'
      });
    }

    // Find user
    const user = users.find(u => u.username === username);
    if (!user) {
      return res.status(400).json({
        code: 1005,
        message: 'User not existed'
      });
    }

    // Check password
    const isValidPassword = await comparePassword(password, user.password);
    if (!isValidPassword) {
      return res.status(400).json({
        code: 1006,
        message: 'Unauthenticated'
      });
    }

    // Generate token
    const token = generateToken(user);

    console.log('Login successful for:', username);

    res.json({
      code: 1000,
      message: 'Success',
      result: {
        token: token,
        authenticated: true
      }
    });

  } catch (error) {
    console.error('Login error:', error);
    res.status(500).json({
      code: 9999,
      message: 'Uncategorized error'
    });
  }
});

// User registration endpoint
app.post('/bookstore/users', async (req, res) => {
  try {
    const { username, password, firstName, lastName, email, phone, dob } = req.body;

    console.log('Registration attempt:', { username, email });

    // Input validation
    if (!username || username.length < 4) {
      return res.status(400).json({
        code: 1003,
        message: 'Username must be at least 4 characters'
      });
    }

    if (!password || password.length < 8) {
      return res.status(400).json({
        code: 1004,
        message: 'Password must be at least 8 characters'
      });
    }

    if (!email || !email.includes('@')) {
      return res.status(400).json({
        code: 1012,
        message: 'Invalid email format'
      });
    }

    // Check if user exists
    if (users.find(u => u.username === username)) {
      return res.status(400).json({
        code: 1002,
        message: 'User existed'
      });
    }

    if (users.find(u => u.email === email)) {
      return res.status(400).json({
        code: 1013,
        message: 'Email already existed'
      });
    }

    // Create new user
    const hashedPassword = await hashPassword(password);
    const newUser = {
      id: (users.length + 1).toString(),
      username,
      password: hashedPassword,
      firstName,
      lastName,
      email,
      phone,
      dob,
      roles: ['USER']
    };

    users.push(newUser);

    console.log('Registration successful for:', username);

    // Return user without password
    const { password: _, ...userResponse } = newUser;
    res.json({
      code: 1000,
      message: 'Success',
      result: userResponse
    });

  } catch (error) {
    console.error('Registration error:', error);
    res.status(500).json({
      code: 9999,
      message: 'Uncategorized error'
    });
  }
});

// Get users (admin only)
app.get('/bookstore/users', authenticateToken, (req, res) => {
  // Check if user is admin
  const user = users.find(u => u.username === req.user.username);
  if (!user || !user.roles.includes('ADMIN')) {
    return res.status(403).json({
      code: 1007,
      message: 'You do not have permission'
    });
  }

  const usersResponse = users.map(({ password, ...user }) => user);
  res.json({
    code: 1000,
    message: 'Success',
    result: usersResponse
  });
});

// Error handling middleware
app.use((error, req, res, next) => {
  console.error('Unhandled error:', error);
  res.status(500).json({
    code: 9999,
    message: 'Uncategorized error'
  });
});

// Start server
const startServer = async () => {
  await initializeAdmin();

  app.listen(PORT, () => {
    console.log(`🚀 Bookstore Backend (Mock) is running on http://localhost:${PORT}`);
    console.log(`📍 Health check: http://localhost:${PORT}/bookstore/health`);
    console.log(`🔐 Default admin: username=admin, password=admin123`);
    console.log('');
    console.log('Available endpoints:');
    console.log('  POST /bookstore/auth/token - Authentication');
    console.log('  POST /bookstore/users - User registration');
    console.log('  GET  /bookstore/users - Get all users (admin only)');
    console.log('  GET  /bookstore/health - Health check');
  });
};

startServer();

module.exports = app;
