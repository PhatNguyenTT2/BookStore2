import axios from 'axios'

// Base API configuration
const API_BASE_URL = 'http://localhost:8080'

// Create axios instance
const api = axios.create({
  baseURL: API_BASE_URL,
  timeout: 5000,
  headers: {
    'Content-Type': 'application/json'
  }
})

// Request interceptor to add auth token
api.interceptors.request.use(
  config => {
    const token = localStorage.getItem('token')
    if (token && token !== 'demo-offline-token') {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// Response interceptor to handle errors
api.interceptors.response.use(
  response => {
    return response
  },
  error => {
    if (error.response?.status === 401) {
      // Token expired or invalid
      localStorage.removeItem('token')
      window.location.href = '/'
    }
    return Promise.reject(error)
  }
)

// API methods
export const authAPI = {
  login: (credentials) => api.post('/api/auth/token', credentials),
}

export const bookAPI = {
  getAll: () => api.get('/api/books'),
  getById: (id) => api.get(`/api/books/${id}`),
  create: (book) => api.post('/api/books', book),
  update: (id, book) => api.put(`/api/books/${id}`, book),
  delete: (id) => api.delete(`/api/books/${id}`)
}

export const userAPI = {
  getAll: () => api.get('/api/users'),
  getById: (id) => api.get(`/api/users/${id}`),
  create: (user) => api.post('/api/users', user),
  update: (id, user) => api.put(`/api/users/${id}`, user),
  delete: (id) => api.delete(`/api/users/${id}`)
}

export default api
