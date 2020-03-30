#!/bin/bash

echo 'Building Backend Services...'

echo 'Building User-Service...'
cd user-service \
    && ./mvnw clean install \
    && cd - || exit

echo 'Building Customer-Service...'
cd customer-service \
    && ./mvnw clean install \
    && cd -

echo 'Building Order-Service...'
cd order-service \
    && ./gradlew clean build \
    && cd -

echo 'Building Product-Service...'
cd product-service \
    && ./mvnw clean install \
    && cd -

echo 'Building Sales Order System app...'
cd sales-order-system \
    && npm run build \
    && cd -