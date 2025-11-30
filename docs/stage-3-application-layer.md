# Application Layer Structure - All Microservices

## Overview

All application layer code is **pure Java** with **NO Spring annotations**, using **Java records** for DTOs and **constructor injection** for use cases.

---

## 📦 Member Service Application

```
member/src/main/java/com/training/member/application/
├── dto/
│   ├── RegisterRequest.java (record)
│   ├── LoginRequest.java (record)
│   └── MemberResponse.java (record)
└── usecase/
    ├── RegisterMemberUseCase.java
    └── LoginMemberUseCase.java
```

### DTOs:

- **[RegisterRequest](file:///d:/office/training/training-project-2025-11/member/src/main/java/com/training/member/application/dto/RegisterRequest.java)** - `email, password`
- **[LoginRequest](file:///d:/office/training/training-project-2025-11/member/src/main/java/com/training/member/application/dto/LoginRequest.java)** - `email, password`
- **[MemberResponse](file:///d:/office/training/training-project-2025-11/member/src/main/java/com/training/member/application/dto/MemberResponse.java)** - `UUID id, email, createdAt`

### Use Cases:

- **[RegisterMemberUseCase](file:///d:/office/training/training-project-2025-11/member/src/main/java/com/training/member/application/usecase/RegisterMemberUseCase.java)**
  - Dependencies: `MemberRepository`, `PasswordHasher`
  - Behavior: Check email exists → Hash password → Create Member → Save → Return DTO
- **[LoginMemberUseCase](file:///d:/office/training/training-project-2025-11/member/src/main/java/com/training/member/application/usecase/LoginMemberUseCase.java)**
  - Dependencies: `MemberRepository`, `PasswordHasher`
  - Behavior: Find by email → Verify password → Return Member

---

## 🚪 API Gateway Application

```
api-gateway/src/main/java/com/training/apigateway/application/
├── dto/
│   ├── LoginRequest.java (record)
│   └── LoginResponse.java (record)
├── port/
│   └── MemberAuthPort.java (interface)
└── usecase/
    └── AuthenticateUserUseCase.java
```

### DTOs:

- **[LoginRequest](file:///d:/office/training/training-project-2025-11/api-gateway/src/main/java/com/training/apigateway/application/dto/LoginRequest.java)** - `email, password`
- **[LoginResponse](file:///d:/office/training/training-project-2025-11/api-gateway/src/main/java/com/training/apigateway/application/dto/LoginResponse.java)** - `token`

### Port (Interface):

- **[MemberAuthPort](file:///d:/office/training/training-project-2025-11/api-gateway/src/main/java/com/training/apigateway/application/port/MemberAuthPort.java)** - Abstraction for member service communication

### Use Cases:

- **[AuthenticateUserUseCase](file:///d:/office/training/training-project-2025-11/api-gateway/src/main/java/com/training/apigateway/application/usecase/AuthenticateUserUseCase.java)**
  - Dependencies: `MemberAuthPort`, `JwtProvider`
  - Behavior: Verify credentials via member service → Generate JWT → Return token

---

## 📦 Product Service Application

```
product/src/main/java/com/training/product/application/
├── dto/
│   ├── ProductResponse.java (record)
│   └── PagedProductResponse.java (record)
└── usecase/
    ├── SearchProductUseCase.java
    └── GetProductDetailUseCase.java
```

### DTOs:

- **[ProductResponse](file:///d:/office/training/training-project-2025-11/product/src/main/java/com/training/product/application/dto/ProductResponse.java)** - `UUID id, name, description, price, category`
- **[PagedProductResponse](file:///d:/office/training/training-project-2025-11/product/src/main/java/com/training/product/application/dto/PagedProductResponse.java)** - `List<ProductResponse> items, page, size, total`

### Use Cases:

- **[SearchProductUseCase](file:///d:/office/training/training-project-2025-11/product/src/main/java/com/training/product/application/usecase/SearchProductUseCase.java)**
  - Dependencies: `ProductRepository`
  - Behavior: Search by keyword with pagination → Map to DTOs
- **[GetProductDetailUseCase](file:///d:/office/training/training-project-2025-11/product/src/main/java/com/training/product/application/usecase/GetProductDetailUseCase.java)**
  - Dependencies: `ProductRepository`
  - Behavior: Find by ID → Throw exception if not found → Map to DTO

---

## 🛒 Cart Service Application

```
cart/src/main/java/com/training/cart/application/
├── dto/
│   ├── AddCartItemRequest.java (record)
│   ├── CartItemResponse.java (record)
│   └── CartResponse.java (record)
└── usecase/
    ├── AddItemToCartUseCase.java
    ├── RemoveItemFromCartUseCase.java
    └── GetCartUseCase.java
```

### DTOs:

- **[AddCartItemRequest](file:///d:/office/training/training-project-2025-11/cart/src/main/java/com/training/cart/application/dto/AddCartItemRequest.java)** - `UUID productId, int quantity`
- **[CartItemResponse](file:///d:/office/training/training-project-2025-11/cart/src/main/java/com/training/cart/application/dto/CartItemResponse.java)** - `UUID productId, int quantity`
- **[CartResponse](file:///d:/office/training/training-project-2025-11/cart/src/main/java/com/training/cart/application/dto/CartResponse.java)** - `UUID userId, List<CartItemResponse> items`

### Use Cases:

- **[AddItemToCartUseCase](file:///d:/office/training/training-project-2025-11/cart/src/main/java/com/training/cart/application/usecase/AddItemToCartUseCase.java)**
  - Dependencies: `CartRepository`
  - Behavior: Load or create cart → Add item (aggregate logic) → Save → Return DTO
- **[RemoveItemFromCartUseCase](file:///d:/office/training/training-project-2025-11/cart/src/main/java/com/training/cart/application/usecase/RemoveItemFromCartUseCase.java)**
  - Dependencies: `CartRepository`
  - Behavior: Load cart → Remove item (aggregate logic) → Save → Return DTO
- **[GetCartUseCase](file:///d:/office/training/training-project-2025-11/cart/src/main/java/com/training/cart/application/usecase/GetCartUseCase.java)**
  - Dependencies: `CartRepository`
  - Behavior: Load cart or empty → Map to DTO

---

## 📊 Summary

| Service         | DTOs   | Use Cases | Ports/Interfaces | Total Files |
| --------------- | ------ | --------- | ---------------- | ----------- |
| **Member**      | 3      | 2         | 0                | 5           |
| **API Gateway** | 2      | 1         | 1                | 4           |
| **Product**     | 2      | 2         | 0                | 4           |
| **Cart**        | 3      | 3         | 0                | 6           |
| **TOTAL**       | **10** | **8**     | **1**            | **19**      |

---

## ✅ Clean Architecture Compliance

All application layer code follows these strict rules:

1. ✅ **Pure Java** - No Spring annotations
2. ✅ **Java Records** - All DTOs use Java 16+ record syntax
3. ✅ **Constructor Injection** - All use cases use constructor-based DI
4. ✅ **DTO Mapping** - Never return domain objects directly
5. ✅ **Single Responsibility** - Each use case handles one business operation
6. ✅ **Dependency Inversion** - Use cases depend on domain interfaces (repositories)
7. ✅ **Port/Adapter Pattern** - MemberAuthPort for external service communication

---

## 🎯 Key Application Behaviors

### Member Service

- **Registration**: Email uniqueness check, password hashing, member creation
- **Login**: Credential verification using password hasher strategy

### API Gateway

- **Authentication**: Coordinates with member service and JWT generation
- **Port Pattern**: MemberAuthPort abstracts member service communication

### Product Service

- **Search**: Keyword-based search with pagination support
- **Detail Retrieval**: Product lookup with exception handling

### Cart Service

- **Add Item**: Uses Cart aggregate's addItem() method (auto-merges duplicates)
- **Remove Item**: Uses Cart aggregate's removeItem() method
- **Get Cart**: Returns empty cart if not found (graceful handling)

---

## 🔍 Maven Build Status

✅ **BUILD SUCCESS** - All application code compiled successfully with no errors.

---

## 💡 Design Notes

### Use Case Pattern

Each use case follows a consistent structure:

1. Constructor injection of dependencies
2. Single `execute()` method
3. Business logic orchestration
4. DTO mapping before returning
5. No Spring annotations

### DTO as Records

All DTOs use Java records providing:

- Immutability by default
- Automatic equals/hashCode
- Automatic toString
- Compact syntax
- Type safety

### Separation of Concerns

- **Domain Layer**: Business rules and entities
- **Application Layer**: Use case orchestration and DTOs
- **Infrastructure Layer**: (Next stage) Controllers, repositories, configs
