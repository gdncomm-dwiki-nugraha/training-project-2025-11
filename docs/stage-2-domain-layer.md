# Domain Layer Structure - All Microservices

## Overview

All domain layer code is **pure Java** with **NO Spring annotations**, following Clean Architecture principles.

---

## 📦 Member Service Domain

```
member/src/main/java/com/training/member/domain/
├── model/
│   └── Member.java
├── repository/
│   └── MemberRepository.java
├── service/
│   └── PasswordHasher.java (Strategy Pattern)
└── exception/
    ├── MemberAlreadyExistsException.java
    └── InvalidCredentialException.java
```

### Files Created:

- **[Member.java](file:///d:/office/training/training-project-2025-11/member/src/main/java/com/training/member/domain/model/Member.java)** - Entity with UUID id, email, passwordHash, createdAt
- **[MemberRepository.java](file:///d:/office/training/training-project-2025-11/member/src/main/java/com/training/member/domain/repository/MemberRepository.java)** - Interface: findByEmail(), save(), findById()
- **[PasswordHasher.java](file:///d:/office/training/training-project-2025-11/member/src/main/java/com/training/member/domain/service/PasswordHasher.java)** - Strategy interface: hash(), matches()
- **[MemberAlreadyExistsException.java](file:///d:/office/training/training-project-2025-11/member/src/main/java/com/training/member/domain/exception/MemberAlreadyExistsException.java)**
- **[InvalidCredentialException.java](file:///d:/office/training/training-project-2025-11/member/src/main/java/com/training/member/domain/exception/InvalidCredentialException.java)**

### Design Pattern:

✅ **Strategy Pattern** - PasswordHasher allows different hashing strategies (BCrypt, Argon2, etc.)

---

## 🚪 API Gateway Domain

```
api-gateway/src/main/java/com/training/apigateway/domain/
├── model/
│   └── AuthUser.java
├── service/
│   ├── JwtProvider.java
│   └── JwtProviderFactory.java (Factory Pattern)
└── exception/
    └── InvalidJwtException.java
```

### Files Created:

- **[AuthUser.java](file:///d:/office/training/training-project-2025-11/api-gateway/src/main/java/com/training/apigateway/domain/model/AuthUser.java)** - Model with UUID userId
- **[JwtProvider.java](file:///d:/office/training/training-project-2025-11/api-gateway/src/main/java/com/training/apigateway/domain/service/JwtProvider.java)** - Interface: generate(), validate()
- **[JwtProviderFactory.java](file:///d:/office/training/training-project-2025-11/api-gateway/src/main/java/com/training/apigateway/domain/service/JwtProviderFactory.java)** - Factory interface: createProvider(type)
- **[InvalidJwtException.java](file:///d:/office/training/training-project-2025-11/api-gateway/src/main/java/com/training/apigateway/domain/exception/InvalidJwtException.java)**

### Design Pattern:

✅ **Factory Pattern** - JwtProviderFactory creates different JWT provider implementations (HS256, RS256)

---

## 📦 Product Service Domain

```
product/src/main/java/com/training/product/domain/
├── model/
│   └── Product.java
├── repository/
│   └── ProductRepository.java
├── service/
│   └── Specification.java (Specification Pattern)
└── exception/
    └── ProductNotFoundException.java
```

### Files Created:

- **[Product.java](file:///d:/office/training/training-project-2025-11/product/src/main/java/com/training/product/domain/model/Product.java)** - Entity with UUID id, name, description, BigDecimal price, category
- **[ProductRepository.java](file:///d:/office/training/training-project-2025-11/product/src/main/java/com/training/product/domain/repository/ProductRepository.java)** - Interface: findById(), search(), save(), findAll()
- **[Specification.java](file:///d:/office/training/training-project-2025-11/product/src/main/java/com/training/product/domain/service/Specification.java)** - Generic interface: isSatisfiedBy(T)
- **[ProductNotFoundException.java](file:///d:/office/training/training-project-2025-11/product/src/main/java/com/training/product/domain/exception/ProductNotFoundException.java)**

### Design Pattern:

✅ **Specification Pattern** - Generic Specification<T> interface for encapsulating search/filter business rules

---

## 🛒 Cart Service Domain

```
cart/src/main/java/com/training/cart/domain/
├── model/
│   ├── Cart.java (Aggregate Root)
│   └── CartItem.java (Value Object)
├── repository/
│   └── CartRepository.java
└── exception/
    ├── CartNotFoundException.java
    └── CartItemNotFoundException.java
```

### Files Created:

- **[Cart.java](file:///d:/office/training/training-project-2025-11/cart/src/main/java/com/training/cart/domain/model/Cart.java)** - Aggregate root with business methods:
  - `addItem(productId, quantity)` - Adds or updates item
  - `removeItem(productId)` - Removes item from cart
  - `updateItemQuantity(productId, newQuantity)` - Updates item quantity
  - `clear()` - Clears all items
  - `getTotalItems()` - Returns total item count
  - `isEmpty()` - Checks if cart is empty
- **[CartItem.java](file:///d:/office/training/training-project-2025-11/cart/src/main/java/com/training/cart/domain/model/CartItem.java)** - Value object with UUID productId, int quantity
- **[CartRepository.java](file:///d:/office/training/training-project-2025-11/cart/src/main/java/com/training/cart/domain/repository/CartRepository.java)** - Interface: findByUserId(), save(), deleteByUserId()
- **[CartNotFoundException.java](file:///d:/office/training/training-project-2025-11/cart/src/main/java/com/training/cart/domain/exception/CartNotFoundException.java)**
- **[CartItemNotFoundException.java](file:///d:/office/training/training-project-2025-11/cart/src/main/java/com/training/cart/domain/exception/CartItemNotFoundException.java)**

### Design Pattern:

✅ **Aggregate Pattern** - Cart is the aggregate root that encapsulates CartItem and all business logic

---

## 📊 Summary

| Service         | Entities           | Repositories | Services/Interfaces      | Exceptions | Design Pattern |
| --------------- | ------------------ | ------------ | ------------------------ | ---------- | -------------- |
| **Member**      | 1 (Member)         | 1            | 1 (PasswordHasher)       | 2          | Strategy       |
| **API Gateway** | 1 (AuthUser)       | 0            | 2 (JwtProvider, Factory) | 1          | Factory        |
| **Product**     | 1 (Product)        | 1            | 1 (Specification)        | 1          | Specification  |
| **Cart**        | 2 (Cart, CartItem) | 1            | 0                        | 2          | Aggregate      |

**Total Domain Files Created**: 18

---

## ✅ Clean Architecture Compliance

All domain layer code follows these strict rules:

1. ✅ **Pure Java** - No Spring annotations
2. ✅ **No Framework Dependencies** - Only Java standard library
3. ✅ **Repository Interfaces** - Infrastructure will implement
4. ✅ **Domain Exceptions** - Custom exceptions for business rules
5. ✅ **Design Patterns** - Each service implements its designated pattern
6. ✅ **Encapsulation** - Business logic inside domain models (especially Cart aggregate)
7. ✅ **Value Objects** - Immutable semantics (CartItem)
8. ✅ **Aggregate Roots** - Cart manages its own internal state

---

## 🎯 Key Domain Behaviors

### Member Service

- Email uniqueness validation
- Password hashing abstraction (strategy)
- Credential validation

### API Gateway

- JWT generation and validation
- Multiple JWT algorithm support (factory)
- Authentication user representation

### Product Service

- Product catalog management
- Generic specification for filtering
- Search with pagination

### Cart Service

- **Aggregate Pattern** with rich domain logic:
  - Automatically merges duplicate products
  - Validates quantity constraints
  - Maintains cart consistency
  - Encapsulates item management
  - Provides read-only access to items (Collections.unmodifiableList)

---

## 🔍 Maven Build Status

✅ **BUILD SUCCESS** - All domain code compiled successfully with no errors.
