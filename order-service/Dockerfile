FROM gradle:6.2.2-jdk8 as build
WORKDIR /app
COPY . .
RUN gradle build --no-daemon -x test

FROM openjdk:8-alpine
COPY --from=build /app/build/libs/order-service-*.jar /usr/local/bin/order-service.jar
RUN chmod +x /usr/local/bin/order-service.jar
ENTRYPOINT ["java", "-jar", "/usr/local/bin/order-service.jar"]