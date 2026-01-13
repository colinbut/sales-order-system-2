FROM gradle:8.7-jdk21 as build
WORKDIR /app
COPY . .
RUN gradle build --no-daemon -x test

FROM openjdk:21-slim
COPY --from=build /app/build/libs/order-service-*.jar /usr/local/bin/order-service.jar
RUN chmod +x /usr/local/bin/order-service.jar
ENTRYPOINT ["java", "-jar", "/usr/local/bin/order-service.jar"]
