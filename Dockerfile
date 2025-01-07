FROM openjdk:17-oracle

# Install Maven by downloading and extracting it
RUN curl -fsSL https://archive.apache.org/dist/maven/maven-3/3.8.6/binaries/apache-maven-3.8.6-bin.tar.gz -o maven.tar.gz && \
    tar -xzf maven.tar.gz -C /opt && \
    rm maven.tar.gz && \
    ln -s /opt/apache-maven-3.8.6/bin/mvn /usr/bin/mvn

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and the source files to the container
COPY pom.xml .
COPY src ./src

# Install dependencies and build the application
RUN mvn clean package -DskipTests

# Debug: List the contents of the target directory to verify the JAR file exists
RUN ls -la /app/target

# Ensure the target directory exists and copy the .jar file into the container
RUN mkdir -p /app/target
COPY ./target/kibou-registry.jar /app/kibou-registry.jar

EXPOSE 9000

ENTRYPOINT ["java", "-jar", "/app/kibou-registry.jar"]
