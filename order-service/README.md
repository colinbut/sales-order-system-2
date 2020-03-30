# Order Service

This is a backend microservice which provides services to interact with 'orders'.

This is merely a very simple "entity-driven" microservice as it is just purely a CRUD wrapper.

This microservice uses a NoSQL MongoDB datastore. The choice of this datastore for this demo was purely aimed at utilizing different types of databases for each backend microservice of this whole project.

Currently, the features of this microservice are:
- Create new orders
- Fetch list of all orders
- Get list of orders for a particular customer

## Local Mode

Run directly from your IDE or from CLI using following command:

```bash
./gradlew bootRun
```

You don't have to install MongoDB but if have it then can just launch it:

```bash
mongo
```

note that credentials are defaulted for pure simplicity sake.

Otherwise, can simply launch a docker container for it (via Docker-Compose):

```bash
docker-compose up -d order-service-db-mongodb
```

## Development Mode

You can run this microservice in "Development mode" which is a mode that allows firing up the Order Service tech stack. This mode can be used for simple demo with the team or for functional testing by QAs/Testers.

This sub project is docker-composed to achieve this:

```bash
docker-compose up -d --build
```

## Staging Mode

[TBD]

## Production Mode

[TBD]

## Project Lifecycle

This following section explains the what/how of the project lifecycle phases.

### Implementation

Coding as you please.

### Unit Testing

Unit Testing is done using JUnit 5 which comes prebundled with Spring Boot.

Your IDE should provide first class support for writing the unit tests.

Run unit tests directly from IDE or from CLI using the following command:

```bash
./gradlew clean test
```

### Integration Testing

Using RestAssured to test the controller API endpoints in a fully integrated environment (i.e. the full Spring Application Context is started up). 

Using the TestContainers project to launch docker container of MongoDB in order for Spring framework to connect to it in the tests.

```bash
./gradlew clean test
```

### Building

First require to build a jar artifact:

```bash
./gradlew clean build
```

Then construct the Docker image:

```bash
docker build -t order-service .
```

## Functional Testing (using Postman)

For doing functional testing of this microservice you can use a http client. I like to use Postman.

All of the API endpoints of this microservice are authenticated and thus require the user to be authorized to execute them. See Authenticated section below.

## API Documentation

You can access the documentation for the API using Swagger:

```bash
http://{order-service-url}:8082/swagger-ui.html
```

## Authentication

As this whole project uses JWT (JSON Web Tokens) for authentication, it is required to get a valid JWT token first from the __User-Service__.

User-Service maintains a list of valid users of the Sales Order System application so it is required to have a valid user to use for it to return a valid JWT token. If required, sign up a new user. Please see __User Service__ for more details on this setup.

Once obtained a valid JWT token, first make a request to:

```bash
http://{user-service-url}:8080/authenticate
```

with the following body in request:

```json
{
    "username": [a valid username here],
    "password": [a valid password here]
}
```

Http Client Postman handles most header defaults for you but if using other mechanism such as Curl then must ensure:

- Content-Type of 'application/json' is set

## Technologies

- Java 8
- Spring:
    - Spring Boot
    - Spring Data MongoDB
    - Spring Security
      - JWT
- MongoDB
- Swagger
- Docker:
    - Docker Compose
- Gradle
- RestAssured
- TestContainers
  - TestContainers-SpringBoot