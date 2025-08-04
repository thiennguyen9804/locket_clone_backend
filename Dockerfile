# Stage 1: Build the application using Maven and Java 21
FROM maven:3.9.8-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy pom.xml and source code
COPY . .

# Build Spring Boot app
RUN mvn clean package -DskipTests

# Stage 2: Run the built JAR using minimal base image
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copy only the built JAR
COPY --from=builder /app/target/locket_clone_backend-0.0.1-SNAPSHOT.jar app.jar

# Expose port
EXPOSE 8181

# Start Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=docker", "-DskipTests"]
