#!/usr/bin/env bash

if [ $# -eq 0 ]
then
    echo -n "Usage: $0 docker_image_name [directory where Dockerfile is]"
    exit 1
fi

if [ ! -z ${IS_GIT_TAG+x} ] && [ -z ${GIT_TAG_NAME+x} ]
then
    echo -n "When IS_GIT_TAG is set, GIT_TAG_NAME should be set too"
    exit 1
fi

IMAGE=${1}
DOCKERFILE_DIR=${2:-./}
BRANCH=${BRANCH:-master}
IS_GIT_TAG=${IS_GIT_TAG:-false}

OLD_PWD=$PWD
cd ${DOCKERFILE_DIR}

if [ ! -f ./Dockerfile ]; then
    echo -n "No Dockerfile found in current working directory."
    exit 1
fi

docker build -t ${IMAGE}:${BRANCH} .
docker push ${IMAGE}:${BRANCH}

if [ "$IS_GIT_TAG" == "true" ]
then
    docker tag ${IMAGE}:${BRANCH} ${IMAGE}:$GIT_TAG_NAME
    docker push ${IMAGE}:${GIT_TAG_NAME}
fi

if [ "$BRANCH" == "master" ]
then
    docker tag ${IMAGE}:${BRANCH} ${IMAGE}:latest
    docker push ${IMAGE}:latest
fi

cd ${OLD_PWD}
