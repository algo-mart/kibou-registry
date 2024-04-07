FROM openjdk:17-oracle
COPY target/*.jar kibou-registry.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "kibou-registry.jar"]