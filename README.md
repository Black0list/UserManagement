The API will be available at `http://localhost:8080`
## API Endpoints
### Base URL
http://localhost:8080/api

Authentication Endpoints
Login
Authenticate a user with email and password.
Endpoint: POST /api/auth/login
Request Body:
``` json
{
  "email": "user@example.com",
  "password": "password123"
}
```

Success Response (200 OK):
``` json
{
"message": "Successfully Logged"
}
```

Error Responses:
400 Bad Request: Invalid credentials
``` json
{
  "message": "Invalid Credentials"
}
```

400 Bad Request: Email not found
``` json
{
    "message": "email not found"
}
```

 
User Management Endpoints
Create User
Create a new user account.
Endpoint: POST /api/users
Request Body:
``` json
{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "password": "password123",
  "role": "USER"
}
```

Field Constraints:
name: Required, 3-100 characters
email: Required, valid email format
password: Required, minimum 6 characters
role: Optional, defaults to USER (values: USER, ADMIN)
Success Response (201 Created):
``` json
{
"id": 1,
"name": "John Doe",
"email": "john.doe@example.com",
"role": "USER",
"active": true,
"created_at": "2025-10-24T10:30:00"
}
```

Error Response (400 Bad Request):
``` json
{
  "message": "Email already exists"
}
```


List All Users
Retrieve all users in the system.
Endpoint: GET /api/users
Success Response (200 OK):
``` json
[
    {
    "id": 1,
    "name": "John Doe",
    "email": "john.doe@example.com",
    "role": "USER",
    "active": true,
    "created_at": "2025-10-24T10:30:00"
    },
    {
    "id": 2,
    "name": "Jane Smith",
    "email": "jane.smith@example.com",
    "role": "ADMIN",
    "active": true,
    "created_at": "2025-10-24T11:00:00"
    }
]
```

 
Get User by ID
Retrieve a specific user by their ID.
Endpoint: GET /api/users/{id}
Path Parameters:
id (Long): User ID
Success Response (302 Found):
``` json
{
  "id": 1,
  "name": "John Doe",
  "email": "john.doe@example.com",
  "role": "USER",
  "active": true,
  "created_at": "2025-10-24T10:30:00"
}
```

Error Response (400 Bad Request):
``` json
{
    "message": "User not found"
}
```

 
Update User
Update an existing user's information (partial update supported).
Endpoint: PUT /api/users/{id}
Path Parameters:
id (Long): User ID
Request Body (all fields optional):
``` json
{
  "name": "John Updated",
  "email": "john.updated@example.com",
  "password": "newpassword123",
  "role": "ADMIN",
  "active": false
}
```

Field Constraints:
name: 3-100 characters (if provided)
email: Valid email format (if provided)
password: Minimum 6 characters (if provided)
role: USER or ADMIN (if provided)
active: Boolean (if provided)
Success Response (200 OK):
``` json
{
    "id": 1,
    "name": "John Updated",
    "email": "john.updated@example.com",
    "role": "ADMIN",
    "active": false,
    "created_at": "2025-10-24T10:30:00"
}
```

Error Responses (400 Bad Request):
``` json
{
  "message": "User not found"
}
```
``` json

{
  "message": "Email already exists"
}
```

Delete User
Delete a user (soft delete by default, hard delete optional).
Endpoint: DELETE /api/users/{id}
Path Parameters:
id (Long): User ID
Query Parameters:
hard (Boolean): Optional, default false
false: Soft delete (sets active to false)
true: Hard delete (permanently removes from database)
Examples:
Soft delete: DELETE /api/users/1 or DELETE /api/users/1?hard=false
Hard delete: DELETE /api/users/1?hard=true
Success Response (204 No Content):
```
No body returned
```

Error Response (400 Bad Request):
``` json
{
  "message": "User not found"
}
```


