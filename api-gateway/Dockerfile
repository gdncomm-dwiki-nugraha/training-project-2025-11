# Stage 1: Build
FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /build

# Copy parent pom and all module poms
COPY pom.xml .
COPY api-gateway/pom.xml ./api-gateway/
COPY member/pom.xml ./member/
COPY product/pom.xml ./product/
COPY cart/pom.xml ./cart/

# Download dependencies (layer cache)
RUN mvn dependency:go-offline -pl api-gateway -am || true

# Copy all source code
COPY api-gateway/src ./api-gateway/src
COPY member/src ./member/src
COPY product/src ./product/src
COPY cart/src ./cart/src

# Build the application
RUN mvn -pl api-gateway -am clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy the built jar from builder stage
COPY --from=builder /build/api-gateway/target/*.jar app.jar

# Expose port
EXPOSE 8080

# Run with prod profile
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]
