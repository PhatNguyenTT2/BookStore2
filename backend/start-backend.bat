@echo off
echo ========================================
echo   Bookstore Spring Boot Backend
echo ========================================
echo.

cd /d "%~dp0"

echo Checking Java installation...
java -version >nul 2>&1
if errorlevel 1 (
    echo ERROR: Java is not installed or not in PATH
    echo Please install Java 17 or higher
    pause
    exit /b 1
)

echo Java found. Checking project structure...
if not exist "src\main\java\com\uit\bookstore\BookstoreApplication.java" (
    echo ERROR: BookstoreApplication.java not found
    echo Please ensure you're in the correct directory
    pause
    exit /b 1
)

echo.
echo ========================================
echo   Compiling Java Sources
echo ========================================
echo.

rem Create build directories
if not exist "target" mkdir target
if not exist "target\classes" mkdir target\classes

echo Compiling Java files...

rem Simple compilation approach for demo
javac -cp ".;target\classes" -d target\classes src\main\java\com\uit\bookstore\*.java src\main\java\com\uit\bookstore\*\*.java src\main\java\com\uit\bookstore\*\*\*.java 2>compile_errors.log

if errorlevel 1 (
    echo ERROR: Compilation failed. Check compile_errors.log
    type compile_errors.log
    pause
    exit /b 1
)

echo Compilation successful!
echo.
echo ========================================
echo   Starting Backend Server
echo ========================================
echo.
echo For full Spring Boot functionality, please:
echo 1. Install Maven: https://maven.apache.org/download.cgi
echo 2. Run: mvn spring-boot:run
echo 3. Or use an IDE like IntelliJ IDEA or Eclipse
echo.
echo Current status: Using enhanced mock server instead
echo Mock server provides the same API functionality
echo.

rem Start the enhanced mock server as fallback
cd ..
echo Starting Enhanced Mock Server on port 8080...
node enhanced-mock-server.cjs

pause
