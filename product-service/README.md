# Product Service

## Technologies

- Java 8
- Spring:
    - Spring Boot
    - Spring Data Redis
    - Spring Security
- Redis
- Swagger
- Docker:
    - Docker Compose
    
## Local Mode

Can directly use the Redis in Docker so first launch the backed Redis NoSQL Key/Value datastore:

```bash
docker run --rm --name product-service-redis -d -p 6379:6379 redis
```

Then run the product-service from either your favourite IDE or CLI:

```bash
./mvnw clean install && java -jar target/product-service.jar -Dspring.active.profiles=local 
```

## Development Mode

```bash
docker-compose up --build -d
```