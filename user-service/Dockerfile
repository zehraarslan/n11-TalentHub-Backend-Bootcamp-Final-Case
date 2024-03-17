FROM openjdk:21-slim AS builder

# Set working directory
WORKDIR /app

# Copy pom.xml and build the application
COPY pom.xml ./
RUN mvn clean package

# Create a new thinner image for running the application
FROM openjdk:21-slim

# Set working directory
WORKDIR /app

# Copy the JAR file from the builder stage
COPY --from=builder /target/*.jar app.jar

# Expose the port where your application runs (modify if needed)
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
