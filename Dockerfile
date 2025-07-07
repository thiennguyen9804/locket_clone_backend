
# Stage 1: Build the application using Maven
FROM maven:3.9.8-eclipse-temurin-21  AS builder

# Set working directory
WORKDIR /app

# Copy the pom.xml and download dependencies first
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the source code
COPY . .

# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Run the application using a lightweight Java runtime
FROM eclipse-temurin:21-jdk-alpine

# Set working directory
WORKDIR /app

# Copy the jar from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8181

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
