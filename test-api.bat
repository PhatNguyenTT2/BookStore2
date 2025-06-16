@echo off
echo === Testing Bookstore API ===
echo.

set BASE_URL=http://localhost:8080/bookstore

echo 1. Testing Health Check...
curl -X GET "%BASE_URL%/health" -H "Content-Type: application/json"
echo.
echo.

echo 2. Testing User Registration...
curl -X POST "%BASE_URL%/users" -H "Content-Type: application/json" -d "{\"username\":\"testuser\",\"password\":\"testpass123\",\"firstName\":\"Test\",\"lastName\":\"User\",\"email\":\"test@example.com\",\"phone\":\"1234567890\"}"
echo.
echo.

echo 3. Testing Authentication with correct credentials...
curl -X POST "%BASE_URL%/auth/token" -H "Content-Type: application/json" -d "{\"username\":\"admin\",\"password\":\"admin123\"}"
echo.
echo.

echo 4. Testing Authentication with wrong credentials...
curl -X POST "%BASE_URL%/auth/token" -H "Content-Type: application/json" -d "{\"username\":\"admin\",\"password\":\"wrongpass\"}"
echo.
echo.

echo === Test Complete ===
pause
