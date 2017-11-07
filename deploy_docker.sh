#!/usr/bin/env bash

if [ $# -eq 0 ]
then
    echo -n "Usage: $0 docker_image_name"
fi

if [ ! -f ./Dockerfile ]; then
    echo -n "No Dockerfile found in current working directory."
fi

if [ -z ${BRANCH+x} ] || [ -z ${IS_GIT_TAG+x} ] || [ -z ${GIT_TAG_NAME+x} ]
then
    echo -n "Environment variables: BRANCH, IS_GIT_TAG, GIT_TAG_NAME are not set."
fi

IMAGE=$1

docker build -t $IMAGE:$BRANCH .
docker push $IMAGE:$BRANCH

if [ "$IS_GIT_TAG" == "true" ]
then
    docker tag $IMAGE:$BRANCH $IMAGE:$GIT_TAG_NAME
    docker push $IMAGE:$GIT_TAG_NAME
fi

if [ "$BRANCH" == "master" ]
then
    docker tag $IMAGE:$BRANCH $IMAGE:latest
    docker push $IMAGE:latest
fi
