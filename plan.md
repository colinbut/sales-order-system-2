# Migration Plan: Java 8 to Java 21

This document outlines the plan to migrate the sales-order-system from Java 8 to Java 21.

## Current Status

The project is a microservices-based application with services written in Java (Maven and Gradle) and a frontend in Node.js. The project was failing to build due to a combination of issues, which have been resolved. The project is now successfully building on GitHub Actions.

## Migration Plan

The migration will be done service by service to minimize the risk and make it easier to identify and fix any issues that may arise.

### 1. Analyze each service for Java 8 specific features and dependencies

For each service, I will analyze the `pom.xml` or `build.gradle` to identify any dependencies that are not compatible with Java 21. I will also look for any Java 8 specific code that needs to be updated.

### 2. Update the `pom.xml` or `build.gradle` files

I will update the `java.version` property in the build files of each service to `21`. I will also update the dependencies to the latest versions that are compatible with Java 21.

### 3. Update the `Dockerfile`s

I will update the `FROM` instruction in the `Dockerfile` of each service to use a Java 21 base image. For example, `maven:3.9.6-eclipse-temurin-21-alpine` and `openjdk:21-slim`.

### 4. Update the GitHub Actions workflow

Separate GitHub Actions workflows have been created for each service. Each workflow will be triggered independently when changes occur within its specific service directory.

### 5. Address any code changes

After updating the Java version and dependencies, there might be some code changes required. I will address these changes as they come up.

### 6. Test each service individually

After migrating each service, the dedicated GitHub Actions workflow will run to ensure that everything is working as expected.

### 7. Test the entire application

After migrating all the services, the entire application will be tested locally or in a dedicated CI/CD environment to ensure that all the services are working together correctly.

## Migration Order

I will migrate the services in the following order:

1.  `user-service` (currently being migrated)
2.  `customer-service`
3.  `product-service`
4.  `order-service`
5.  `sales-order-system` (frontend)

I have already started the migration of the `user-service`. The next step is to commit and push all the changes related to the new GitHub Actions workflow structure.
