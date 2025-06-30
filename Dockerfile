# Use Maven to build the project first
FROM maven:3.9.6-eclipse-temurin-23 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Then use a smaller image just to run the app
FROM eclipse-temurin:23-jdk
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]


