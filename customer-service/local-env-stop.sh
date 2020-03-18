#!/bin/bash

docker kill customer-service && docker rm customer-service
docker kill customer-db-mysql && docker rm customer-db-mysql