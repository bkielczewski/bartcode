#!/usr/bin/env bash

if [ $# -eq 0 ]
then
    echo -n "Usage: $0 docker_repository_prefix"
    exit 1
fi

./deploy_config.sh

IGNORE_FILES=$(ls -p | grep -v /)
CHANGED_COMPONENTS=`git diff --name-only ${SHIPPABLE_COMMIT_RANGE} | sort -u | awk 'BEGIN {FS="/"} {print $1}' | uniq`

for component in ${CHANGED_COMPONENTS}
do
    if [[ ! " ${IGNORE_FILES[@]} " =~ "$component" ]] && [ -f "$component/Dockerfile" ]; then
        ./deploy_docker.sh $1/${component} ${component}
    fi
done
