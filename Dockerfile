# Stage 1: Build the application
FROM maven:3.8.5-openjdk-17 AS build

# Set the working directory inside the container
WORKDIR /usr/src/kibou-registry

# Copy the pom.xml and source files
COPY pom.xml .
COPY src ./src

# Build the project and create the .jar file
RUN mvn clean package -DskipTests

# Stage 2: Create the final image
FROM openjdk:17-jdk-slim

# Set the working directory inside the final image
WORKDIR /kibou-registry

# Copy the .jar file from the build stage
COPY --from=build /usr/src/kibou-registry/target/kibou-registry.jar ./kibou-registry.jar

# Expose the required port (adjust if necessary)
EXPOSE 9000

# Run the application
ENTRYPOINT ["java", "-jar", "kibou-registry.jar"]
