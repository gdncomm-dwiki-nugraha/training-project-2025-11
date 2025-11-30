# Spring Boot Monorepo Documentation

This directory contains comprehensive documentation for each stage of the Spring Boot Monorepo Bootstrap project.

## Project Overview

A marketplace microservices system built with Spring Boot 3.4.12, Java 21, Maven multi-module, and Clean Architecture (Hexagonal).

## Documentation by Stage

### [Stage 1: Project Structure](stage-1-project-structure.md)

Initial monorepo setup with Maven multi-module configuration and folder structure for all 4 microservices.

### [Stage 2: Domain Layer](stage-2-domain-layer.md)

Pure Java domain models, repository interfaces, domain services, and exceptions - NO Spring annotations.

### [Stage 3: Application Layer](stage-3-application-layer.md)

Use cases, DTOs (Java Records), and application ports - orchestration layer between domain and infrastructure.

### [Stage 4: Infrastructure Layer](stage-4-infrastructure-layer.md)

Controllers, JPA entities, repository implementations, security, and Spring configurations.

### [Stage 5: Integration Status](stage-5-integration-status.md)

Complete integration with exception handlers, application.yml configurations, and REST API endpoints.

### [Stage 6.1: Test Generation](stage-6-test-generation.md)

Automated test suite with unit tests, controller tests, and test principles.

### [Stage 6.2: POM Regeneration](stage-6-pom-regeneration.md)

Fixed Maven POM files with correct dependencies, parent references, and build configurations.

### [Stage 7: Docker Deployment](stage-7-docker-deployment.md)

Complete Docker infrastructure with Dockerfiles, docker-compose.yml, and deployment instructions.

## Quick Links

### Microservices

- **API Gateway** (Port 8080) - JWT authentication, routing
- **Member Service** (Port 8081) - User registration/login, PostgreSQL
- **Product Service** (Port 8082) - Product catalog, PostgreSQL
- **Cart Service** (Port 8083) - Shopping cart, Redis

### Tech Stack

- Java 21
- Spring Boot 3.4.12
- PostgreSQL 16
- Redis 7
- Maven 3.9+
- Docker & Docker Compose

### Design Patterns

- **Strategy**: PasswordHasher (Member Service)
- **Factory**: JwtProvider (API Gateway)
- **Chain of Responsibility**: JwtAuthenticationFilter (API Gateway)
- **Specification**: Product search filtering (Product Service)
- **Aggregate**: Cart domain model (Cart Service)
- **Adapter**: All repository implementations

## Architecture Principles

✅ Clean Architecture (Hexagonal)  
✅ Domain-Driven Design  
✅ SOLID Principles  
✅ Dependency Inversion  
✅ Framework Independence (Domain/Application layers)  
✅ Test-Driven Development

## Getting Started

1. Read [Project Structure](stage-1-project-structure.md) for overview
2. Review [POM Regeneration](stage-6-pom-regeneration.md) for build setup
3. Check [Docker Deployment](stage-7-docker-deployment.md) to run the system

## Build Commands

```bash
# Validate POMs
mvn validate

# Compile all modules
mvn clean compile

# Build JARs
mvn clean install -DskipTests

# Run with Docker
docker compose up --build
```

## REST API Summary

| Service     | Endpoints                                           | Database   |
| ----------- | --------------------------------------------------- | ---------- |
| API Gateway | `/api/v1/auth/login`, `/api/v1/auth/logout`         | None       |
| Member      | `/api/v1/members/register`, `/api/v1/members/login` | PostgreSQL |
| Product     | `/api/v1/products`, `/api/v1/products/{id}`         | PostgreSQL |
| Cart        | `/api/v1/cart`, `/api/v1/cart` (POST/DELETE)        | Redis      |

All services expose `/actuator/health` for health checks.
