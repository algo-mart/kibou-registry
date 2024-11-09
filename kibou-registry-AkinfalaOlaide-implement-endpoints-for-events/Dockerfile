# Build stage
FROM maven:3.8.4-openjdk-11 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Runtime stage
FROM openjdk:17-jdk-slim
WORKDIR /app
# Copy the jar file from the build stage to the runtime stage
COPY --from=build /app/target/*.jar kibou-registry.jar
EXPOSE 9000
CMD ["java", "-jar", "kibou-registry.jar"]
