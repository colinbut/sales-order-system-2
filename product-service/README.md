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
    
## Running using Docker (Separately)

```bash
docker network create product-redis-network 
```

First launch the backed Redis NoSQL Key/Value datastore:

```bash
docker run --rm --name product-service-redis --network product-redis-network -d -p 6379:6379 redis
```

Then run the product-service:

```bash
docker run --rm --link product-service-redis:redis --name product-service --network product-redis-network -d -p 8083:8083 product-service
```