# GitHub Secrets Configuration Guide

## Required Secrets for CI/CD

Add these secrets in your GitHub repository:
Settings → Secrets and Variables → Actions → New repository secret

### Heroku Deployment Secrets
- `HEROKU_API_KEY`: Your Heroku API key (get from Heroku Account Settings)
- `HEROKU_EMAIL`: Your Heroku account email
- `HEROKU_APP_NAME`: bookstore-backend-app (or your app name)

### Application Secrets
- `JWT_SECRET`: A secure JWT secret key (minimum 32 characters)
- `DATABASE_URL`: PostgreSQL connection string (auto-set by Heroku)

## Environment Variables for Heroku

Set these in Heroku Dashboard → Settings → Config Vars:

```
SPRING_PROFILES_ACTIVE=prod
JWT_SECRET=YourSecretKeyHereMustBeLongEnoughForHS512Algorithm
CORS_ORIGINS=https://your-frontend-domain.com
```

## Heroku CLI Commands

```bash
# Install Heroku CLI first
# Login
heroku login

# Create app
heroku create bookstore-backend-app

# Add PostgreSQL database
heroku addons:create heroku-postgresql:hobby-dev -a bookstore-backend-app

# Set config vars
heroku config:set SPRING_PROFILES_ACTIVE=prod -a bookstore-backend-app
heroku config:set JWT_SECRET=YourSecretKeyHereMustBeLongEnoughForHS512Algorithm -a bookstore-backend-app

# Deploy
git subtree push --prefix=backend heroku main
```

## Alternative: Railway Deployment

```bash
# Install Railway CLI
npm install -g @railway/cli

# Login and deploy
railway login
railway init
railway up
```
