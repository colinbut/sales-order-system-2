FROM maven:3.9.3-eclipse-temurin-8-alpine as build
WORKDIR /app
COPY . .
RUN mvn package

FROM openjdk:8-alpine
COPY --from=build /app/target/user-service-*.jar /usr/local/bin/user-service.jar
RUN chmod +x /usr/local/bin/user-service.jar
ENTRYPOINT ["java", "-jar", "/usr/local/bin/user-service.jar"]