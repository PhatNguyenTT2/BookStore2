#!/usr/bin/env python3
import json
import http.server
import socketserver
from urllib.parse import urlparse, parse_qs

class MockHandler(http.server.BaseHTTPRequestHandler):
    def do_OPTIONS(self):
        self.send_response(200)
        self.send_header('Access-Control-Allow-Origin', '*')
        self.send_header('Access-Control-Allow-Methods', 'GET, POST, OPTIONS')
        self.send_header('Access-Control-Allow-Headers', 'Content-Type')
        self.end_headers()
    
    def do_POST(self):
        if self.path == '/bookstore/auth/token':
            content_length = int(self.headers['Content-Length'])
            post_data = self.rfile.read(content_length)
            data = json.loads(post_data.decode('utf-8'))
            
            print(f"Login attempt: {data}")
            
            if data.get('username') == 'admin' and data.get('password') == 'admin123':
                response = {
                    "code": 1000,
                    "message": "Success",
                    "result": {
                        "authenticated": True,
                        "token": "mock-jwt-token-123",
                        "user": {"id": 1, "username": "admin", "name": "Administrator", "role": "admin"}
                    }
                }
                self.send_response(200)
            else:
                response = {
                    "code": 1002,
                    "message": "Unauthenticated", 
                    "result": {"authenticated": False}
                }
                self.send_response(401)
            
            self.send_header('Content-Type', 'application/json')
            self.send_header('Access-Control-Allow-Origin', '*')
            self.end_headers()
            self.wfile.write(json.dumps(response).encode())
        else:
            self.send_response(404)
            self.end_headers()
    
    def do_GET(self):
        if self.path == '/bookstore/health':
            self.send_response(200)
            self.send_header('Content-Type', 'application/json')
            self.send_header('Access-Control-Allow-Origin', '*')
            self.end_headers()
            response = {"status": "OK", "message": "Python Mock Server running"}
            self.wfile.write(json.dumps(response).encode())
        else:
            self.send_response(404)
            self.end_headers()

PORT = 8080
with socketserver.TCPServer(("", PORT), MockHandler) as httpd:
    print(f"🐍 Python Mock Server running on http://localhost:{PORT}")
    print(f"📚 Health: http://localhost:{PORT}/bookstore/health")
    httpd.serve_forever()
