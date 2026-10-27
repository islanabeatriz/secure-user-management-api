# UserHub — Secure User Management API

![Java 17](https://img.shields.io/badge/java-17-blue)
![Spring Boot 3.5.10](https://img.shields.io/badge/spring%20boot-3.5.10-brightgreen)
![Build](https://img.shields.io/badge/build-Maven-blue)

REST API for user management built with Java 17 and Spring Boot. The project
provides user CRUD operations, input validation, BCrypt password hashing and
balance management backed by an in-memory H2 database.

## Features

- Create, search, update and delete users
- BCrypt password hashing (passwords are never returned by the API)
- User balance with two-decimal precision
- Bean Validation for request payloads
- Centralized handling for duplicate emails and missing users
- Swagger/OpenAPI documentation
- Controller tests with MockMvc

## Tech stack

- Java 17
- Spring Boot 3.5.10
- Spring Web
- Spring Data JPA
- Spring Security
- Spring Validation
- H2 Database (in-memory)
- Springdoc OpenAPI
- Maven Wrapper

## Requirements

- JDK 17 or newer
- Bash, or use `mvnw.cmd` on Windows

## Running locally

Clone the repository and start the application:

```bash
git clone https://github.com/islanabeatriz/secure-user-management-api.git
cd secure-user-management-api
./mvnw spring-boot:run
```

The API starts at `http://localhost:8080`. The H2 database is in memory, so
data is reset whenever the application stops.

To run the test suite:

```bash
./mvnw test
```

## API documentation

With the application running, access:

- Swagger UI: <http://localhost:8080/swagger-ui/index.html>
- OpenAPI JSON: <http://localhost:8080/v3/api-docs>

## Endpoints

Base URL: `http://localhost:8080`

| Method | Endpoint | Description | Success |
| --- | --- | --- | --- |
| `GET` | `/users/` | Health check | `200 OK` |
| `POST` | `/users` | Create a user | `200 OK` |
| `GET` | `/users/email/{email}` | Find a user by email | `200 OK` |
| `PUT` | `/users/{id}` | Update user fields | `200 OK` |
| `PATCH` | `/users/{id}/balance` | Add to the user balance | `200 OK` |
| `DELETE` | `/users/email/{email}` | Delete a user by email | `204 No Content` |

### Create a user

```bash
curl -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{"email":"ana@example.com","password":"Secret123","name":"Ana"}'
```

Example response:

```json
{
  "id": 1,
  "email": "ana@example.com",
  "name": "Ana",
  "balance": 0
}
```

### Find a user by email

```bash
curl http://localhost:8080/users/email/ana@example.com
```

### Update a user

All fields are optional. When `password` is provided, it is hashed with BCrypt
before being stored.

```bash
curl -X PUT http://localhost:8080/users/1 \
  -H "Content-Type: application/json" \
  -d '{"name":"Ana Silva","password":"NewSecret123"}'
```

### Add balance

The amount must be at least `0.01` and contain no more than two decimal places.

```bash
curl -X PATCH http://localhost:8080/users/1/balance \
  -H "Content-Type: application/json" \
  -d '{"amount":50.00}'
```

### Delete a user

```bash
curl -X DELETE http://localhost:8080/users/email/ana@example.com
```

## Errors and validation

Common responses include:

```json
{
  "message": "User not found"
}
```

- `400 Bad Request`: invalid or missing request data
- `404 Not Found`: user does not exist
- `409 Conflict`: email is already registered

## Security status

Passwords are hashed with `BCryptPasswordEncoder` and are not included in
`UserResponseDTO`. At the moment, the `/users/**` routes are publicly
accessible; authentication and authorization are not implemented yet.

## Architecture

```text
Controller -> Service -> Repository -> H2 Database
     |           |
     |           +-> DTOs and Mapper
     |
     +-> Validation and Exception Handling
```

The main package is organized into controllers, business services, entities,
repositories, DTOs, mappers and infrastructure configuration.

## License

No license has been defined for this repository yet.
