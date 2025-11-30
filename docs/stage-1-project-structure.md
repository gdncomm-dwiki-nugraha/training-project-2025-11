# Project Structure Tree

## training-project-2025-11/

```
training-project-2025-11/
├── .gitignore
├── LICENSE
├── README.md
├── pom.xml (Root Parent)
│
├── api-gateway/
│   ├── README.md
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   ├── java/
│       │   │   └── com/
│       │   │       └── training/
│       │   │           └── apigateway/
│       │   │               ├── ApiGatewayApplication.java
│       │   │               ├── domain/
│       │   │               │   └── .gitkeep
│       │   │               ├── application/
│       │   │               │   └── .gitkeep
│       │   │               └── infrastructure/
│       │   │                   └── .gitkeep
│       │   └── resources/
│       │       └── application.yml
│       └── test/
│           └── java/
│
├── member/
│   ├── README.md
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   ├── java/
│       │   │   └── com/
│       │   │       └── training/
│       │   │           └── member/
│       │   │               ├── MemberApplication.java
│       │   │               ├── domain/
│       │   │               │   └── .gitkeep
│       │   │               ├── application/
│       │   │               │   └── .gitkeep
│       │   │               └── infrastructure/
│       │   │                   └── .gitkeep
│       │   └── resources/
│       │       └── application.yml
│       └── test/
│           └── java/
│
├── product/
│   ├── README.md
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   ├── java/
│       │   │   └── com/
│       │   │       └── training/
│       │   │           └── product/
│       │   │               ├── ProductApplication.java
│       │   │               ├── domain/
│       │   │               │   └── .gitkeep
│       │   │               ├── application/
│       │   │               │   └── .gitkeep
│       │   │               └── infrastructure/
│       │   │                   └── .gitkeep
│       │   └── resources/
│       │       └── application.yml
│       └── test/
│           └── java/
│
└── cart/
    ├── README.md
    ├── pom.xml
    └── src/
        ├── main/
        │   ├── java/
        │   │   └── com/
        │   │       └── training/
        │   │           └── cart/
        │   │               ├── CartApplication.java
        │   │               ├── domain/
        │   │               │   └── .gitkeep
        │   │               ├── application/
        │   │               │   └── .gitkeep
        │   │               └── infrastructure/
        │   │                   └── .gitkeep
        │   └── resources/
        │       └── application.yml
        └── test/
            └── java/
```

## Summary

### Maven Configuration

- **Root POM**: `pom.xml` with packaging=pom, 4 modules, Java 21, Spring Boot 3.4.12
- **Submodule POMs**: Each service has its own `pom.xml` with parent reference

### Services

#### 1. API Gateway (Port 8080)

- **Dependencies**: web, security, validation, jjwt
- **Design Pattern**: Factory Pattern for JWT, Chain of Responsibility for filters
- **Package**: `com.training.apigateway`

#### 2. Member Service (Port 8081)

- **Dependencies**: web, jpa, postgresql, validation, security-crypto
- **Design Pattern**: Strategy Pattern for PasswordHasher
- **Package**: `com.training.member`
- **Database**: PostgreSQL (member_db)

#### 3. Product Service (Port 8082)

- **Dependencies**: web, jpa, postgresql, validation
- **Design Pattern**: Specification Pattern for search filtering
- **Package**: `com.training.product`
- **Database**: PostgreSQL (product_db)

#### 4. Cart Service (Port 8083)

- **Dependencies**: web, data-redis, validation
- **Design Pattern**: Aggregate Pattern for Cart
- **Package**: `com.training.cart`
- **Database**: Redis

### Clean Architecture Layers (Each Service)

1. **Domain Layer** (`domain/`)

   - Pure Java classes
   - NO Spring annotations
   - Contains: Entities, domain services, interfaces, exceptions

2. **Application Layer** (`application/`)

   - DTOs and Use Cases
   - No Spring annotations unless necessary
   - Service orchestration

3. **Infrastructure Layer** (`infrastructure/`)
   - Controllers, JPA entities, repository implementations, configs
   - Spring annotations allowed
   - External integrations

### Next Steps

- Implement business logic following Clean Architecture principles
- Add controllers, repositories, use cases as needed
- Configure databases (PostgreSQL, Redis)
- Implement design patterns as specified
