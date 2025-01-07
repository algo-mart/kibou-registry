FROM openjdk:17-oracle

# Install Maven
RUN apt-get update && apt-get install -y maven

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and the source files to the container
COPY pom.xml .
COPY src ./src

# Install dependencies and build the application
RUN mvn clean package -DskipTests

# Copy the built .jar file to the container
COPY target/*.jar kibou-registry.jar

EXPOSE 9000

ENTRYPOINT ["java", "-jar", "kibou-registry.jar"]
