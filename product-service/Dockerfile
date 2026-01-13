FROM maven:3.9.3-eclipse-temurin-8-alpine as build
WORKDIR /app
COPY . .
RUN mvn package

FROM openjdk:8-alpine
COPY --from=build /app/target/product-service-*.jar /usr/local/bin/product-service.jar
RUN chmod +x /usr/local/bin/product-service.jar
ENTRYPOINT ["java", "-jar", "/usr/local/bin/product-service.jar"]