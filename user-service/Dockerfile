FROM openjdk:3.2.3-alpine AS builder

WORKDIR /app

COPY pom.xml ./
RUN mvn clean package

FROM openjdk:3.2.3-alpine

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
