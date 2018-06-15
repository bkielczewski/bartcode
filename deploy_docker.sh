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
BRANCH=${BRANCH:-local}
BUILD=${BUILD_NUMBER:-local}
IS_GIT_TAG=${IS_GIT_TAG:-false}

if [ ! -f "${DOCKERFILE_DIR}/Dockerfile" ]; then
    echo -n "No Dockerfile found in ${DOCKERFILE_DIR}"
    exit 1
fi

echo -n "Building ${IMAGE}:${BRANCH} in ${DOCKERFILE_DIR}" \n

docker build -f ${DOCKERFILE_DIR}/Dockerfile -t ${IMAGE}:${BRANCH} ${DOCKERFILE_DIR}
docker push ${IMAGE}:${BRANCH}

if [ "$IS_GIT_TAG" == "true" ]
then
    echo -n "Tagging ${IMAGE}:${BRANCH} with :$GIT_TAG_NAME" \n
    docker tag ${IMAGE}:${BRANCH} ${IMAGE}:$GIT_TAG_NAME
    docker push ${IMAGE}:${GIT_TAG_NAME}
fi

if [ "$BRANCH" == "master" ]
then
    echo "Tagging ${IMAGE}:${BRANCH} with :latest" \n
    docker tag ${IMAGE}:${BRANCH} ${IMAGE}:latest
    docker push ${IMAGE}:latest
fi

echo -n "Tagging ${IMAGE}:${BRANCH} with :${BRANCH}-${BUILD}" \n
docker tag ${IMAGE}:${BRANCH} ${IMAGE}:${BRANCH}-${BUILD}
docker push ${IMAGE}:${BRANCH}-${BUILD}
