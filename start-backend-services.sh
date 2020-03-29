#!/bin/bash
docker-compose up --build -d user-service customer-db-mysql customer-service order-service-db-mongodb order-service product-service-db-redis product-service

