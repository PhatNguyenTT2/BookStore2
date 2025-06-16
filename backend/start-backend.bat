@echo off
echo Starting Bookstore Backend...
echo.
echo Compiling Java files...

cd /d "e:\UIT\boook\backend"

rem Create output directory
if not exist "build" mkdir build
if not exist "build\classes" mkdir build\classes

rem Download dependencies (simplified approach)
echo Note: This is a simplified version. For production, use Maven or Gradle.
echo.

rem Compile Java files manually (this is a demo script)
echo To properly run this backend, please:
echo 1. Install Maven or Gradle
echo 2. Use an IDE like IntelliJ IDEA or Eclipse
echo 3. Or use Spring Boot CLI
echo.
echo For now, we'll continue with the mock server on port 8080...

pause
