# Product Service

## Running using Docker (Separately)

First launch the backed Redis NoSQL Key/Value datastore:

```bash
docker run --rm --name product-service-redis -d -p 6379:6379 redis
```

Then run the product-service:

```bash
docker run --rm --name product-service -d -p 8083:8083 product-service
```