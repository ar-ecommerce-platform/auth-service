# 🔐 Auth Service

Authentication and authorization microservice for the eCommerce platform.  
Manages **user login**, **JWT generation**, and **token validation**.

---

## 🧭 Overview

- Exposes REST APIs for user authentication and token issuance.
- Uses **Spring Security** and **JWT** for stateless authentication.
- Integrates with **PostgreSQL** (production) and **H2** (development).

---

## 🧪 API Endpoints

| Method | Endpoint | Description |
|---------|-----------|-------------|
| `POST` | `/auth/register` | Register a new user |
| `POST` | `/auth/login` | Authenticate user and return JWT |
| `GET` | `/auth/validate` | Validate JWT token |

---

## ▶️ Run Locally

```bash
./gradlew bootRun
```

---

## 🧱 Docker

```bash
docker build -t ecommerce/auth-service .
docker run -d -p 8081:8081 ecommerce/auth-service
```

---

## 🧰 Environment Variables

| Variable | Description |
|-----------|-------------|
| `JWT_SECRET` | Secret key for token signing |
| `DB_URL` | JDBC connection string |
| `DB_USERNAME` | Database username |
| `DB_PASSWORD` | Database password |

---

## 🧰 Related Services

| Service | Port | Purpose |
|----------|------|----------|
| Discovery Server | 8761 | Service registry |
| Config Server | 8888 | Centralized configuration management |
| API Gateway | 8080 | Routes traffic |
| Auth Service | 8081 | Auth / JWT |
| User Service | 8082 | User data |
