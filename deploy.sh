#!/bin/bash

# BookStore Backend Deployment Script

echo "🚀 Starting BookStore Backend Deployment..."

# Build application
echo "📦 Building application..."
cd backend
./mvnw clean package -DskipTests

# Heroku deployment
echo "🌐 Deploying to Heroku..."

# Login to Heroku (requires Heroku CLI)
# heroku login

# Create Heroku app (run once)
# heroku create bookstore-backend-app

# Add PostgreSQL addon
# heroku addons:create heroku-postgresql:hobby-dev -a bookstore-backend-app

# Set environment variables
echo "⚙️ Setting environment variables..."
# heroku config:set SPRING_PROFILES_ACTIVE=prod -a bookstore-backend-app
# heroku config:set JWT_SECRET=YourSecretKeyHereMustBeLongEnoughForHS512Algorithm -a bookstore-backend-app
# heroku config:set CORS_ORIGINS=* -a bookstore-backend-app

# Deploy
echo "🚀 Deploying to Heroku..."
# git subtree push --prefix=backend heroku main

echo "✅ Deployment completed!"
echo "🌐 Your app will be available at: https://bookstore-backend-app.herokuapp.com"
