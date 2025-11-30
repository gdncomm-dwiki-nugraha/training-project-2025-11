# Stage 7: Docker Deployment

## Overview

Complete Docker infrastructure for all microservices with PostgreSQL, Redis, and service networking.

## Files Generated

### Dockerfiles (Multi-stage Builds)

1. **[api-gateway/Dockerfile](file:///d:/office/training/training-project-2025-11/api-gateway/Dockerfile)**
   - Base: maven:3.9-eclipse-temurin-21 (builder), eclipse-temurin:21-jre (runtime)
   - Port: 8080
2. **[member/Dockerfile](file:///d:/office/training/training-project-2025-11/member/Dockerfile)**

   - Base: maven:3.9-eclipse-temurin-21 (builder), eclipse-temurin:21-jre (runtime)
   - Port: 8081

3. **[product/Dockerfile](file:///d:/office/training/training-project-2025-11/product/Dockerfile)**

   - Base: maven:3.9-eclipse-temurin-21 (builder), eclipse-temurin:21-jre (runtime)
   - Port: 8082

4. **[cart/Dockerfile](file:///d:/office/training/training-project-2025-11/cart/Dockerfile)**
   - Base: maven:3.9-eclipse-temurin-21 (builder), eclipse-temurin:21-jre (runtime)
   - Port: 8083

### Docker Compose

**[docker-compose.yml](file:///d:/office/training/training-project-2025-11/docker-compose.yml)** - Root directory

## Infrastructure Components

### Databases

1. **member-db** (PostgreSQL 16)

   - Database: memberdb
   - User: member / member123
   - Port: 5432
   - Health check: pg_isready

2. **product-db** (PostgreSQL 16)

   - Database: productdb
   - User: product / product123
   - Port: 5433 (external)
   - Health check: pg_isready

3. **redis** (Redis 7)
   - Port: 6379
   - Health check: PING

### Microservices

All services run with `--spring.profiles.active=prod`

1. **member-service** → member-db
2. **product-service** → product-db
3. **cart-service** → redis
4. **api-gateway** → all services

### Service Networking

Services communicate via service names:

- `http://member-service:8081`
- `http://product-service:8082`
- `http://cart-service:8083`

## Usage

### Start All Services

```bash
docker compose up --build
```

### Stop All Services

```bash
docker compose down
```

### Clean Up Volumes

```bash
docker compose down -v
```

## Service URLs

- API Gateway: http://localhost:8080
- Member Service: http://localhost:8081
- Product Service: http://localhost:8082
- Cart Service: http://localhost:8083

## Persistent Volumes

- `member-db-data` - PostgreSQL data for Member Service
- `product-db-data` - PostgreSQL data for Product Service
- `redis-data` - Redis data for Cart Service

## Environment Variables

### Member Service

- `DB_HOST=member-db`
- `DB_PORT=5432`
- `DB_NAME=memberdb`
- `DB_USER=member`
- `DB_PASSWORD=member123`

### Product Service

- `DB_HOST=product-db`
- `DB_PORT=5432`
- `DB_NAME=productdb`
- `DB_USER=product`
- `DB_PASSWORD=product123`

### Cart Service

- `REDIS_HOST=redis`
- `REDIS_PORT=6379`

### API Gateway

- `JWT_SECRET=change-this-in-prod`
- `SERVICES_MEMBER=http://member-service:8081`
- `SERVICES_PRODUCT=http://product-service:8082`
- `SERVICES_CART=http://cart-service:8083`

## Build Details

### Multi-stage Build

1. **Builder Stage**: Compile with Maven in Java 21 container
2. **Runtime Stage**: Run with JRE 21 (smaller image)

### Optimizations

- Layer caching for dependencies
- Minimal runtime image (JRE only)
- Health checks for databases
- Service dependencies with health conditions
