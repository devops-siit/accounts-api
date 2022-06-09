FROM maven:3.8.5-openjdk-18 AS build
COPY src /src
COPY pom.xml /
RUN mvn -f /pom.xml clean package -Dmaven.test.skip

FROM openjdk:18-oracle
COPY --from=build target/accounts-api-0.0.1-SNAPSHOT.jar /accounts-api-0.0.1.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/accounts-api-0.0.1.jar"]