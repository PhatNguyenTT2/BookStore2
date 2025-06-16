# 📚 Bookstore - Library Management System

A comprehensive library management system built with **Vue.js 3** frontend and **Spring Boot** backend, featuring modern UI/UX design and complete CRUD operations for books, users, branches, and transactions.

## 🚀 Features

### 📖 **Core Functionality**
- **Books Management** - Add, edit, delete, search books with categories
- **Users Management** - User registration, authentication, role-based access
- **Branch Management** - Multiple library branches support
- **Import/Export Receipts** - Track book inventory transactions
- **Payment Management** - Handle fines, fees, and payments
- **Reports & Analytics** - Generate various reports and statistics

### 🔐 **Authentication & Security**
- JWT-based authentication
- Role-based authorization (Admin, Librarian, User)
- Secure password hashing with bcrypt
- Session management and token validation

### 🎨 **Modern UI/UX**
- **Vue.js 3** with Composition API
- **Vuetify 3** Material Design components
- **Dark theme** support
- **Responsive design** for all devices
- **Pinia** state management
- **Vue Router** for navigation

## 🛠️ Tech Stack

### **Frontend**
- **Vue.js 3** - Progressive JavaScript framework
- **Vite** - Fast build tool and dev server
- **Vuetify 3** - Material Design component framework
- **Pinia** - State management
- **Vue Router** - Client-side routing
- **Axios** - HTTP client for API calls

### **Backend**
- **Spring Boot 3.2** - Java framework
- **Spring Security** - Authentication and authorization
- **Spring Data JPA** - Database access layer
- **JWT** - JSON Web Tokens for auth
- **H2 Database** (Development) / **MySQL** (Production)
- **Maven/Gradle** - Build tools

## 📦 Installation & Setup

### **Prerequisites**
- Node.js 18+ 
- Java 17+
- Git

### **1. Clone Repository**
```bash
git clone https://github.com/0914461195tm/BookStore.git
cd BookStore

See [Vite Configuration Reference](https://vite.dev/config/).

## Project Setup

```sh
npm install
```

### Compile and Hot-Reload for Development

```sh
npm run dev
```

### Compile and Minify for Production

```sh
npm run build
```
=======
# bookstore-app
>>>>>>> 149a3af9b111cb745c79de95cf2d57e363890c2c
```

### **2. Frontend Setup**
```bash
# Install dependencies
npm install

# Start development server
npm run dev

# The app will be available at http://localhost:5173
```

### **3. Backend Setup (Enhanced Mock Server)**
```bash
# Install backend dependencies
npm install express cors bcrypt jsonwebtoken

# Start mock server
node enhanced-mock-server.cjs

# Server will run on http://localhost:8080
```

### **4. Backend Setup (Spring Boot - Production)**
```bash
cd backend

# Using Maven
mvn clean install
mvn spring-boot:run

# Using Gradle
./gradlew build
./gradlew bootRun
```

## 🎯 Quick Start

### **Demo Login**
- **Username**: `admin`
- **Password**: `admin123`
- **Role**: Administrator

### **API Endpoints**
```
GET  /bookstore/health           - Health check
POST /bookstore/auth/token       - User authentication
POST /bookstore/users            - User registration
GET  /bookstore/users            - Get all users (admin only)
```

## 📁 Project Structure

```
BookStore/
├── src/                          # Vue.js frontend source
│   ├── components/               # Vue components
│   │   ├── account/             # Authentication components
│   │   └── admin/               # Admin dashboard components
│   ├── assets/                  # Static assets, icons, styles
│   ├── data/                    # Mock data and sample data
│   └── plugins/                 # Vue plugins configuration
├── backend/                     # Spring Boot backend
│   └── src/main/java/com/uit/bookstore/
│       ├── controller/          # REST controllers
│       ├── service/             # Business logic layer
│       ├── repository/          # Data access layer
│       ├── entity/              # JPA entities
│       ├── dto/                 # Data Transfer Objects
│       ├── security/            # Security configuration
│       └── config/              # Application configuration
├── enhanced-mock-server.cjs     # Development mock server
└── public/                      # Static public files
```

## 🖥️ Screenshots

### Login Page
- Modern sliding authentication forms
- Support for Sign In, Sign Up, Forgot Password, OTP verification
- Responsive design with animated transitions

### Dashboard
- Clean, intuitive interface
- Real-time statistics and analytics
- Dark theme with Material Design

### Books Management
- Advanced search and filtering
- Category-based organization
- Bulk operations support

## 🔧 Development

### **Available Scripts**

#### Frontend
```bash
npm run dev          # Start development server
npm run build        # Build for production
npm run preview      # Preview production build
```

#### Backend (Mock Server)
```bash
node enhanced-mock-server.cjs    # Start enhanced mock API server
node simple-mock-server.cjs      # Start simple mock server
```

### **API Testing**
```bash
# Test health endpoint
curl http://localhost:8080/bookstore/health

# Test authentication
curl -X POST http://localhost:8080/bookstore/auth/token \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

## 🌟 Key Features

### **Authentication System**
- ✅ JWT-based authentication
- ✅ Role-based access control
- ✅ Secure password hashing
- ✅ Token validation and refresh
- ✅ Offline demo mode for development

### **Books Management**
- ✅ CRUD operations for books
- ✅ Advanced search and filtering
- ✅ Category management
- ✅ Inventory tracking
- ✅ Book availability status

### **User Management**
- ✅ User registration and authentication
- ✅ Role assignment (Admin, Librarian, User)
- ✅ Profile management
- ✅ User activity tracking

### **Branch Management**
- ✅ Multiple library branches
- ✅ Branch-specific inventory
- ✅ Manager assignment
- ✅ Branch performance analytics

## 🚧 Roadmap

### **Phase 1 (Current)**
- ✅ Basic authentication system
- ✅ Mock backend server
- ✅ Frontend UI components
- ✅ User and books management

### **Phase 2 (In Progress)**
- 🔄 Complete Spring Boot backend
- 🔄 Database integration
- 🔄 Advanced book operations
- 🔄 Import/Export receipts

### **Phase 3 (Planned)**
- 📋 Payment processing
- 📋 Report generation
- 📋 Email notifications
- 📋 Mobile app support

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Authors

- **Team** - *Initial work* - [BookStore](https://github.com/0914461195tm/BookStore)

## 🙏 Acknowledgments

- Vue.js community for excellent documentation
- Vuetify team for beautiful Material Design components
- Spring Boot team for robust backend framework
- All contributors and testers

---

## 📞 Support

If you have any questions or need support, please:

1. Check the [Issues](https://github.com/0914461195tm/BookStore/issues) page
2. Create a new issue if your problem isn't already reported
3. Contact the development team

**Happy Coding! 🎉**
