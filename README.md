# Ecom Platform — Backend

A small e-commerce REST API built with Spring Boot 3 (Java 21). Users register, log in
with JWT and manage a shopping cart; admins manage the product catalogue. Data is stored
in a file-based H2 database.

## Requirements

- Java 21
- Maven

## Configuration

Configuration is read from environment variables (see `src/main/resources/application.properties`):

| Variable                | Required | Default                    | Description                                    |
|-------------------------|----------|----------------------------|------------------------------------------------|
| `APP_SECRET`            | yes      | —                          | JWT signing secret, at least 32 bytes (HS256)  |
| `APP_ADMIN_PASSWORD`    | yes      | —                          | Password for the seeded admin account          |
| `APP_ADMIN_USERNAME`    | no       | `admin`                    | Username of the seeded admin account           |
| `APP_ADMIN_EMAIL`       | no       | `admin@example.com`        | Email of the seeded admin account              |
| `APP_EXPIRATION`        | no       | `60`                       | JWT lifetime in minutes                        |
| `DB_URL`                | no       | `jdbc:h2:file:./db/testdb` | JDBC URL                                       |
| `APP_DATABASE_USERNAME` | no       | `sa`                       | Database username                              |
| `APP_DATABASE_PASSWORD` | no       | *(empty)*                  | Database password                              |

## Running

```bash
export APP_SECRET='change-me-to-a-random-string-of-32+-bytes'
export APP_ADMIN_PASSWORD='change-me'
mvn spring-boot:run
```

The API listens on `http://localhost:8080`. An admin account is seeded on first start.

## API overview

| Method | Path                  | Access        | Description                                            |
|--------|-----------------------|---------------|--------------------------------------------------------|
| GET    | `/`                   | public        | HATEOAS index                                          |
| POST   | `/auth/user`          | public        | Log in (`{"username","password"}`) → `{"token"}`       |
| POST   | `/auth/admin`         | public        | Log in as admin → `{"token"}`                          |
| POST   | `/auth/login`         | public        | Validate an existing token (`{"token"}`)               |
| POST   | `/user/user`          | public        | Register (`{"username","password","email"}`)           |
| GET    | `/user/all`           | admin         | List users (without password hashes)                   |
| GET    | `/products`           | authenticated | List products                                          |
| POST   | `/products`           | admin         | Create a product                                       |
| DELETE | `/products/{id}`      | admin         | Delete a product (also removes it from all carts)      |
| GET    | `/cart/products`      | authenticated | List the caller's cart items                           |
| POST   | `/cart/{productId}`   | authenticated | Add a product to the caller's cart                     |
| DELETE | `/cart/product/{id}`  | authenticated | Remove a product from the caller's cart                |
| GET    | `/cart/all`           | admin         | List all cart ids                                      |
| DELETE | `/cart/{id}`          | admin         | Delete a cart                                          |

Authenticated endpoints expect an `Authorization: Bearer <token>` header.

Product lists are returned as positional string arrays
(`[name, description, price, id, image]`) for compatibility with the legacy JavaFX
client on the `frontend` branch.
