# Microservices Architecture: User, Order Services, and API Gateway

## Overview
This project demonstrates a microservices architecture built with Spring Boot, consisting of:
- **User Service**: Manages user registration, authentication, and profile management.
- **Order Service**: Handles order creation, retrieval, and management.
- **API Gateway**: Facilitates communication between services, secures endpoints with JWT-based authentication, and provides unified access.

---

## Features
### User Service
- **User Management**:
  - Register new users.
  - Authenticate users and generate JWT tokens.
  - Retrieve user details by ID.
  - Update and delete user profiles.
- **Validation**:
  - Enforces constraints on required fields like `username` and `password`.

### Order Service
- **Order Management**:
  - Create new orders.
  - Retrieve orders by ID or user.
  - Update and delete orders.
- **Validation**:
  - Ensures required fields like `product` and `quantity` are provided.

### API Gateway
- **Routing**:
  - Directs API requests to the appropriate microservices.
- **Security**:
  - Secures endpoints with JWT authentication.
  - Provides a unified entry point for the application.
- **Simplified Access**:
  - Exposes user and order APIs under a single gateway endpoint.

---

## Technologies Used
- **Backend Framework**: Spring Boot 3.4
- **Database**: H2 Database
- **ORM**: Hibernate
- **Dependency Management**: Maven
- **API Gateway**: Spring Cloud Gateway
- **Security**: Spring Security with JWT
- **Testing**: Postman

---

## Setup and Run Instructions
### Clone the Repository
```bash
$ git clone <repository-url>
$ cd microservices-architecture
```

### Build the Project
```bash
$ mvn clean install
```

### Run the Services
#### User Service
```bash
$ cd user-service
$ mvn spring-boot:run
```
The User Service will start on `http://localhost:8082`.

#### Order Service
```bash
$ cd order-service
$ mvn spring-boot:run
```
The Order Service will start on `http://localhost:8083`.

#### API Gateway
```bash
$ cd api-gateway
$ mvn spring-boot:run
```
The API Gateway will start on `http://localhost:8083`.

---

## API Endpoints
### User Service
- **Register User**: `POST /users/register`
  - Request Body:
    ```json
    {
      "username": "sampleuser",
      "password": "securepassword"
    }
    ```
- **Authenticate User**: `POST /users/login`
  - Request Body:
    ```json
    {
      "username": "sampleuser",
      "password": "securepassword"
    }
    ```
  - Response:
    ```json
    {
      "token": "<JWT Token>"
    }
    ```

### Order Service
- **Create Order**: `POST /orders`
  - Headers: `Authorization: Bearer <JWT Token>`
  - Request Body:
    ```json
    {
      "userId": 1,
      "product": "Laptop",
      "quantity": 1
    }
    ```
- **Get Orders by User**: `GET /orders/user/{userId}`
  - Headers: `Authorization: Bearer <JWT Token>`

### API Gateway
- **Routing**:
  - User Service: `/users/**`
  - Order Service: `/orders/**`
- **Authentication**:
  - Requires JWT tokens for secure access.
  - Example:
    ```bash
    curl -H "Authorization: Bearer <JWT Token>" http://localhost:8085/orders
    ```

---

## Database Schema
### User Table
| Column      | Type        | Constraints         |
|-------------|-------------|---------------------|
| `id`        | BIGINT      | Primary Key         |
| `username`  | VARCHAR(255)| NOT NULL, Unique    |
| `password`  | VARCHAR(255)| NOT NULL            |

### Order Table
| Column      | Type        | Constraints         |
|-------------|-------------|---------------------|
| `id`        | BIGINT      | Primary Key         |
| `user_id`   | BIGINT      | NOT NULL            |
| `product`   | VARCHAR(255)| NOT NULL            |
| `quantity`  | INT         | NOT NULL            |

---




