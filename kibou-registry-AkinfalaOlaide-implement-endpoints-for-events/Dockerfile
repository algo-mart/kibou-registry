# Build stage
FROM maven:3.8.4-openjdk-11 AS build
COPY . /app
WORKDIR /app
RUN mvn clean package -DskipTests


FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the application JAR file into the container
COPY target/kibou-registry.jar /app/kibou-registry.jar

# Expose the port on which the application will run
EXPOSE 9000

# Command to run the application
CMD ["java", "-jar", "/app/kibou-registry.jar"]
