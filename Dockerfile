FROM openjdk:17-oracle
COPY target/*.jar kibou-registry.jar
EXPOSE 9000
ENTRYPOINT ["java", "-jar", "kibou-registry.jar"]