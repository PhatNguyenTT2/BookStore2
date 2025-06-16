# 🚀 MANUAL DEPLOYMENT GUIDE

## 📋 BƯỚC 1: Chuẩn bị Heroku Account

1. **Đăng ký tài khoản Heroku**
   - Truy cập: https://signup.heroku.com/
   - Đăng ký với email của bạn

2. **Download Heroku CLI** 
   - Windows: https://devcenter.heroku.com/articles/heroku-cli#download-and-install
   - Hoặc download trực tiếp: https://cli-assets.heroku.com/heroku-x64.exe

## 📋 BƯỚC 2: Setup Project cho Heroku

### Current Status ✅
- ✅ Procfile đã có sẵn
- ✅ PostgreSQL dependency đã thêm
- ✅ Production profile đã cấu hình
- ✅ GitHub repository đã sẵn sàng

### Files quan trọng:
```
backend/
├── Procfile                    # Heroku startup command
├── pom.xml                     # Dependencies (PostgreSQL added)
└── src/main/resources/
    └── application-prod.properties  # Production config
```

## 📋 BƯỚC 3: Deploy Commands

Sau khi cài Heroku CLI, chạy các lệnh sau:

```bash
# 1. Login to Heroku
heroku login

# 2. Create Heroku app
heroku create bookstore-backend-phat

# 3. Add PostgreSQL database
heroku addons:create heroku-postgresql:mini -a bookstore-backend-phat

# 4. Set environment variables
heroku config:set SPRING_PROFILES_ACTIVE=prod -a bookstore-backend-phat
heroku config:set JWT_SECRET=YourSecretKeyHereMustBeLongEnoughForHS512Algorithm -a bookstore-backend-phat
heroku config:set JWT_EXPIRATION=86400000 -a bookstore-backend-phat

# 5. Deploy from GitHub (Option A)
# Hoặc connect GitHub repo trên Heroku Dashboard

# 6. Deploy from local (Option B)
git push heroku main
```

## 📋 BƯỚC 4: Alternative - Railway Deploy

Nếu Heroku khó setup, thử Railway:

1. **Truy cập**: https://railway.app
2. **Login với GitHub**
3. **Deploy từ Repository**: Chọn BookStore2 repo
4. **Environment Variables**:
   ```
   SPRING_PROFILES_ACTIVE=prod
   JWT_SECRET=YourSecretKeyHereMustBeLongEnoughForHS512Algorithm
   JWT_EXPIRATION=86400000
   ```
5. **Railway sẽ tự động**:
   - Tạo PostgreSQL database
   - Build và deploy application
   - Cấp subdomain cho app

## 📋 BƯỚC 5: Testing Deployment

Sau khi deploy thành công, test API:

```bash
# Health check
curl https://your-app-name.herokuapp.com/api/health

# Authentication test
curl -X POST https://your-app-name.herokuapp.com/api/auth/token \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123"
  }'
```

## 📋 BƯỚC 6: GitHub Actions Setup

Để enable auto-deployment, thêm secrets vào GitHub:

1. **Vào GitHub repo > Settings > Secrets and variables > Actions**

2. **Thêm secrets**:
   ```
   HEROKU_API_KEY=your-heroku-api-key
   HEROKU_EMAIL=your-heroku-email
   HEROKU_APP_NAME=bookstore-backend-phat
   ```

3. **File CI/CD workflow** đã có sẵn: `ci-cd-workflow.yml`

## 🎯 Expected Results

Sau khi deployment thành công:

✅ **Backend URLs**:
- Heroku: `https://bookstore-backend-phat.herokuapp.com`
- Railway: `https://bookstore-backend-production.up.railway.app`

✅ **API Endpoints available**:
- `GET /api/health` - Health check
- `POST /api/auth/token` - Authentication
- `GET /api/books` - Get all books
- `POST /api/books` - Create book (admin)
- `GET /api/users` - Get users (admin)
- `POST /api/borrow` - Borrow book
- `GET /api/reports/stats` - Library statistics

✅ **Database**:
- PostgreSQL hosted on cloud
- Tables auto-created by Spring Boot
- Default admin user: `admin/admin123`
- Default regular user: `user/user1234`

---

## 🚨 Next Steps

1. **Deploy Backend** ✅ (Ready)
2. **Update Frontend** to use production API URL
3. **Deploy Frontend** to Vercel/Netlify
4. **Update CORS** settings with frontend URL
5. **Test full-stack integration**

**Ready to deploy! Choose your preferred platform and follow the steps above.** 🚀
