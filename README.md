# auth-service

Authentication for the [ar-ecommerce-platform](https://github.com/ar-ecommerce-platform):
registration, login, and JWT issuance/validation.

- **Port:** 8081
- **Tokens:** HS256 JWT with `iss` + `roles` claims, shared secret with the gateway
- **Persistence:** in-memory H2 (`users` table) — resets on restart
- **Registers with:** Eureka (discovery-server :8761)

## Endpoints

Reached through the gateway as `/api/auth/**`.

| Method | Path | Body | Result |
|---|---|---|---|
| `POST` | `/auth/register` | `{ email, password }` | `201` |
| `POST` | `/auth/login` | `{ email, password }` | `200 { token, tokenType, expiresInMs }` |
| `GET` | `/auth/validate` | header `Authorization: Bearer <t>` | `200 { subject, roles }` or `401` |

Errors: `409` email already registered, `401` bad credentials / invalid token, `400` validation.

**API docs:** Swagger UI at `http://localhost:8081/swagger-ui.html` (OpenAPI JSON at `/v3/api-docs`).

## Run

Whole platform (recommended):

```bash
docker compose -f ../infra/compose/docker-compose.yml up -d --build
```

This service alone:

```bash
./gradlew bootRun
# or
docker build -t ecom/auth-service . && docker run --rm -p 8081:8081 ecom/auth-service
```

## Build & quality

```bash
./gradlew build          # compile + test + spotless + checkstyle (cyclomatic complexity <= 10) + jacoco report
./gradlew spotlessApply
```

Quality config is vendored: `gradle/quality.gradle`, `config/checkstyle/`.

## Config

| Variable | Default | Purpose |
|---|---|---|
| `SERVER_PORT` | `8081` | HTTP port |
| `JWT_SECRET` | dev fallback (>= 32 bytes) | HS256 signing secret — inject the real value from the environment |
| `JWT_EXPIRATION_MS` | `3600000` | token lifetime |
| `JWT_ISSUER` | `ecommerce-auth` | `iss` claim |
| `EUREKA_CLIENT_SERVICEURL_DEFAULTZONE` | `http://localhost:8761/eureka/` | registry URL |

## Tech

Java 21 · Spring Boot 3.5.7 · Spring Security · jjwt 0.13 · Spring Data JPA + H2 ·
Spring Cloud 2025.0.0 (`netflix-eureka-client`) · Gradle
