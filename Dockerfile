FROM maven:3.8.4-openjdk-17-slim AS build

COPY pom.xml /usr/src/blogapp/
COPY src /usr/src/blogapp/src

WORKDIR /usr/src/blogapp

RUN mvn -f /usr/src/blogapp/pom.xml clean package -DskipTests

FROM openjdk:17-jdk-slim

COPY --from=build /usr/src/blogapp/target/*.jar /usr/app/app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/app/app.jar"]
