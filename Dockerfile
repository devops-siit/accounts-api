FROM openjdk:17-oracle
COPY target/accounts-api-0.0.1-SNAPSHOT.jar /accounts-api-0.0.1.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/accounts-api-0.0.1.jar"]