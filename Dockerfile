FROM maven:3.8.5-openjdk-18 AS build
COPY src /src
COPY pom.xml /
RUN --mount=type=cache,target=/root/.m2 mvn -f /pom.xml clean package -P dev -DskipTests=true

FROM openjdk:18-oracle
COPY --from=build target/*.jar /accounts-api.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "/accounts-api.jar"]