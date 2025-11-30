# Infrastructure Layer Structure - All Microservices

## Overview

Infrastructure layer uses **Spring annotations** and framework dependencies (JPA, Redis, Security, WebClient) to implement ports defined by domain/application layers.

---

## 📦 Member Service Infrastructure

```
member/src/main/java/com/training/member/infrastructure/
├── config/
│   └── MemberConfig.java
├── persistence/
│   ├── entity/
│   │   └── MemberEntity.java (@Entity)
│   ├── mapper/
│   │   └── MemberMapper.java (@Component)
│   └── repository/
│       ├── SpringDataMemberRepository.java (@Repository)
│       └── JpaMemberRepository.java (@Component)
├── security/
│   └── BCryptPasswordHasher.java (@Component)
└── web/
    └── MemberController.java (@RestController)
```

### Components:

- **[MemberController](file:///d:/office/training/training-project-2025-11/member/src/main/java/com/training/member/infrastructure/web/MemberController.java)** - REST endpoints for register/login
- **[MemberEntity](file:///d:/office/training/training-project-2025-11/member/src/main/java/com/training/member/infrastructure/persistence/entity/MemberEntity.java)** - JPA entity with table mapping
- **[SpringDataMemberRepository](file:///d:/office/training/training-project-2025-11/member/src/main/java/com/training/member/infrastructure/persistence/repository/SpringDataMemberRepository.java)** - Spring Data JPA interface
- **[JpaMemberRepository](file:///d:/office/training/training-project-2025-11/member/src/main/java/com/training/member/infrastructure/persistence/repository/JpaMemberRepository.java)** - Adapter implementing domain interface
- **[MemberMapper](file:///d:/office/training/training-project-2025-11/member/src/main/java/com/training/member/infrastructure/persistence/mapper/MemberMapper.java)** - Entity/Domain/DTO conversion
- **[BCryptPasswordHasher](file:///d:/office/training/training-project-2025-11/member/src/main/java/com/training/member/infrastructure/security/BCryptPasswordHasher.java)** - Strategy implementation
- **[MemberConfig](file:///d:/office/training/training-project-2025-11/member/src/main/java/com/training/member/infrastructure/config/MemberConfig.java)** - Bean wiring

---

## 🚪 API Gateway Infrastructure

```
api-gateway/src/main/java/com/training/apigateway/infrastructure/
├── adapter/
│   └── MemberAuthPortImpl.java (@Component)
├── config/
│   ├── GatewayConfig.java (@Configuration)
│   └── SecurityConfig.java (@Configuration)
├── security/
│   ├── filter/
│   │   └── JwtAuthenticationFilter.java (@Component)
│   └── jwt/
│       ├── HmacJwtProvider.java
│       └── JwtProviderFactoryImpl.java (@Component)
└── web/
    └── AuthController.java (@RestController)
```

### Components:

- **[AuthController](file:///d:/office/training/training-project-2025-11/api-gateway/src/main/java/com/training/apigateway/infrastructure/web/AuthController.java)** - REST endpoints for login/logout
- **[JwtAuthenticationFilter](file:///d:/office/training/training-project-2025-11/api-gateway/src/main/java/com/training/apigateway/infrastructure/security/filter/JwtAuthenticationFilter.java)** - Chain of Responsibility filter
- **[HmacJwtProvider](file:///d:/office/training/training-project-2025-11/api-gateway/src/main/java/com/training/apigateway/infrastructure/security/jwt/HmacJwtProvider.java)** - JWT HMAC-SHA256 implementation
- **[JwtProviderFactoryImpl](file:///d:/office/training/training-project-2025-11/api-gateway/src/main/java/com/training/apigateway/infrastructure/security/jwt/JwtProviderFactoryImpl.java)** - Factory pattern implementation
- **[MemberAuthPortImpl](file:///d:/office/training/training-project-2025-11/api-gateway/src/main/java/com/training/apigateway/infrastructure/adapter/MemberAuthPortImpl.java)** - RestClient adapter for member service
- **[SecurityConfig](file:///d:/office/training/training-project-2025-11/api-gateway/src/main/java/com/training/apigateway/infrastructure/config/SecurityConfig.java)** - Spring Security configuration
- **[GatewayConfig](file:///d:/office/training/training-project-2025-11/api-gateway/src/main/java/com/training/apigateway/infrastructure/config/GatewayConfig.java)** - Bean wiring

---

## 📦 Product Service Infrastructure

```
product/src/main/java/com/training/product/infrastructure/
├── config/
│   └── ProductConfig.java (@Configuration)
├── persistence/
│   ├── entity/
│   │   └── ProductEntity.java (@Entity)
│   ├── mapper/
│   │   └── ProductMapper.java (@Component)
│   └── repository/
│       ├── SpringDataProductRepository.java (@Repository)
│       └── JpaProductRepository.java (@Component)
└── web/
    └── ProductController.java (@RestController)
```

### Components:

- **[ProductController](file:///d:/office/training/training-project-2025-11/product/src/main/java/com/training/product/infrastructure/web/ProductController.java)** - REST endpoints for search/detail
- **[ProductEntity](file:///d:/office/training/training-project-2025-11/product/src/main/java/com/training/product/infrastructure/persistence/entity/ProductEntity.java)** - JPA entity
- **[SpringDataProductRepository](file:///d:/office/training/training-project-2025-11/product/src/main/java/com/training/product/infrastructure/persistence/repository/SpringDataProductRepository.java)** - Spring Data JPA with custom search query
- **[JpaProductRepository](file:///d:/office/training/training-project-2025-11/product/src/main/java/com/training/product/infrastructure/persistence/repository/JpaProductRepository.java)** - Adapter implementing domain interface
- **[ProductMapper](file:///d:/office/training/training-project-2025-11/product/src/main/java/com/training/product/infrastructure/persistence/mapper/ProductMapper.java)** - Entity/Domain/DTO conversion
- **[ProductConfig](file:///d:/office/training/training-project-2025-11/product/src/main/java/com/training/product/infrastructure/config/ProductConfig.java)** - Bean wiring

---

## 🛒 Cart Service Infrastructure

```
cart/src/main/java/com/training/cart/infrastructure/
├── config/
│   └── CartConfig.java (@Configuration)
├── persistence/
│   ├── mapper/
│   │   └── CartMapper.java (@Component)
│   └── repository/
│       └── RedisCartRepository.java (@Component)
└── web/
    └── CartController.java (@RestController)
```

### Components:

- **[CartController](file:///d:/office/training/training-project-2025-11/cart/src/main/java/com/training/cart/infrastructure/web/CartController.java)** - REST endpoints for cart operations
- **[RedisCartRepository](file:///d:/office/training/training-project-2025-11/cart/src/main/java/com/training/cart/infrastructure/persistence/repository/RedisCartRepository.java)** - Redis adapter with JSON serialization
- **[CartMapper](file:///d:/office/training/training-project-2025-11/cart/src/main/java/com/training/cart/infrastructure/persistence/mapper/CartMapper.java)** - Domain/DTO conversion
- **[CartConfig](file:///d:/office/training/training-project-2025-11/cart/src/main/java/com/training/cart/infrastructure/config/CartConfig.java)** - Redis and bean wiring

---

## 📊 Summary

| Service         | Controllers | Entities | Repositories | Mappers | Configs | Other                  | Total  |
| --------------- | ----------- | -------- | ------------ | ------- | ------- | ---------------------- | ------ |
| **Member**      | 1           | 1        | 2            | 1       | 1       | 1 (Hasher)             | 7      |
| **API Gateway** | 1           | 0        | 0            | 0       | 2       | 4 (JWT/Filter/Adapter) | 7      |
| **Product**     | 1           | 1        | 2            | 1       | 1       | 0                      | 6      |
| **Cart**        | 1           | 0        | 1            | 1       | 1       | 0                      | 4      |
| **TOTAL**       | **4**       | **2**    | **5**        | **3**   | **5**   | **5**                  | **24** |

---

## 🎯 REST API Endpoints

### Member Service (Port 8081)

- `POST /api/v1/members/register` - Register new member
- `POST /api/v1/members/login` - Login member

### API Gateway (Port 8080)

- `POST /api/v1/auth/login` - Authenticate and get JWT token
- `POST /api/v1/auth/logout` - Logout (client-side token removal)

### Product Service (Port 8082)

- `GET /api/v1/products?keyword=...&page=0&size=20` - Search products
- `GET /api/v1/products/{id}` - Get product detail

### Cart Service (Port 8083)

- `GET /api/v1/cart?userId=...` - Get cart
- `POST /api/v1/cart?userId=...` - Add item to cart
- `DELETE /api/v1/cart/{productId}?userId=...` - Remove item from cart

---

## ✅ Design Patterns Implemented

### Member Service

- **Strategy Pattern**: BCryptPasswordHasher implements PasswordHasher
- **Adapter Pattern**: JpaMemberRepository adapts Spring Data JPA to domain interface

### API Gateway

- **Factory Pattern**: JwtProviderFactoryImpl creates JWT providers
- **Chain of Responsibility**: JwtAuthenticationFilter in security filter chain
- **Adapter Pattern**: MemberAuthPortImpl adapts RestClient to domain port

### Product Service

- **Adapter Pattern**: JpaProductRepository adapts Spring Data JPA to domain interface

### Cart Service

- **Adapter Pattern**: RedisCartRepository adapts Redis to domain interface

---

## 🔍 Technology Stack

### Persistence

- **Member & Product**: PostgreSQL with Spring Data JPA
- **Cart**: Redis with RedisTemplate + JSON serialization

### Security

- **Member**: BCrypt password hashing
- **API Gateway**: JWT (HMAC-SHA256), Spring Security, stateless sessions

### Communication

- **API Gateway → Member**: RestClient (HTTP)

---

## 💡 Key Implementation Details

### JPA Repositories

- Use Spring Data JPA for database access
- Custom mapper components convert between entities and domain models
- Spring Data interface + adapter pattern for Clean Architecture compliance

### Redis Repository

- Uses RedisTemplate with String serialization
- Custom JSON serialization/deserialization with Jackson ObjectMapper
- Internal DTOs for Redis storage format

### JWT Implementation

- HMAC-SHA256 algorithm (configurable via factory)
- Configurable secret and expiration
- Extracts JWT from Authorization header or cookie
- Stateless authentication (no server-side sessions)

### Controllers

- All controllers depend ONLY on use cases
- Use @Valid for request validation
- Return DTOs, never domain models
- Follow REST conventions with lowercase hyphen paths

### Configurations

- Each service has a Config class that wires use cases
- Configurations use constructor injection
- Bean definitions for framework components (RedisTemplate, RestClient, etc.)

---

## Member Service compiled successfully!

Member Service infrastructure works correctly with JPA, BCrypt, and REST endpoints.
