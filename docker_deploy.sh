#!/usr/bin/env bash

echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin
docker push bkielczewski/bartcode-frontend
docker push bkielczewski/bartcode-service
