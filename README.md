# Secure User Management API

Portfolio project showcasing a secure, production-minded user management API built with Spring Boot 3, Spring Security, and JPA.

## Highlights
- RESTful CRUD for users
- Password hashing with BCrypt
- Validation with clear error responses
- Centralized error handling
- Tests for endpoints and validation

## Tech Stack
- Java 17
- Spring Boot 3.5.x
- Spring Web, Spring Data JPA, Spring Security
- H2 (in-memory) for dev/test
- Maven

## API Endpoints

Base URL: `http://localhost:8080`

```
POST   /users
GET    /users/
GET    /users/email/{email}
PUT    /users/{id}
DELETE /users/email/{email}
```

### Example Requests

Create user:
```bash
curl -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{"email":"ana@example.com","password":"Secret123","name":"Ana"}'
```

Get user by email:
```bash
curl http://localhost:8080/users/email/ana@example.com
```

Update user:
```bash
curl -X PUT http://localhost:8080/users/1 \
  -H "Content-Type: application/json" \
  -d '{"name":"Ana Silva"}'
```

Delete user:
```bash
curl -X DELETE http://localhost:8080/users/email/ana@example.com
```

## Error Handling

The API returns consistent error responses:
```json
{ "message": "User not found" }
```

Typical error codes:
- `400` for validation errors
- `404` when a user is not found
- `409` when email is already registered

## Running Locally

```bash
./mvnw spring-boot:run
```

If you run in a restricted environment that blocks server sockets, you may need elevated permissions.

## Tests

```bash
./mvnw test
```

## Notes for Portfolio Reviewers

This project focuses on clean layering, secure password handling, and reliable API behavior.  
See the tests for examples of expected API behavior and validation.
