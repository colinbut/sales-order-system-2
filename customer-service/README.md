# Customer Service

## Technologies

- Java 8
- Spring:
    - Spring Boot
    - Spring Data JPA
    - Spring Security
- MySQL
- Swagger
- Docker:
    - Docker Compose

## Development Mode

Running Customer Service in development involves running both the customer-service spring boot application 
and the MySQL database either independently or inside Docker containers.

### Running Locally: Independently

build:
```bash
./mvnw clean install
```

run:
```bash
./mvnw spring-boot:run
```

### Running Locally: Docker containers

1. Run Docker containers separately
2. Run Docker containers using Docker Compose

#### Separate Docker containers

##### Create Network
```bash
docker network create customer-mysql 
```

##### Running MySQL db
```bash
docker run --rm --name customer-db-mysql --network customer-mysql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=customerdb -d mysql:8 
```

##### Run as Docker container

```bash
docker run --name customer-service -d -t --link customer-db-mysql:mysql -p 8080:8080 customer-service
```

#### Using Docker Compose

```bash
docker-compose up --build
```

```bash
docker-compose down
```

## Maintainer

Colin But