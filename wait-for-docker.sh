#!/bin/sh

set -e

while ! docker info > /dev/null 2>&1; do
    echo "Waiting for Docker to be ready..."
    sleep 1
done

echo "Docker is ready!"
