#!/usr/bin/env bash

IGNORE_FILES=$(ls -p | grep -v /)
CHANGED_COMPONENTS=`git diff --name-only ${SHIPPABLE_COMMIT_RANGE} | sort -u | awk 'BEGIN {FS="/"} {print $1}' | uniq`

run_tests() {
    for component in ${CHANGED_COMPONENTS}
    do
        if [[ ! " ${IGNORE_FILES[@]} " =~ "$component" ]] && [ -f "$component/build.gradle.kts" ]; then
            cd "$component"
            ../gradlew test
            cd ..
        fi
    done
}

copy_test_reports() {
    mkdir -p ./shippable/testresults; rsync -a ./*/build/test-results/test/*.xml ./shippable/testresults/
}

run_build() {
    for component in ${CHANGED_COMPONENTS}
    do
        if [[ ! " ${IGNORE_FILES[@]} " =~ "$component" ]] && [ -f "$component/build.gradle.kts" ]; then
            cd "$component"
            ../gradlew build
            cd ..
        fi
    done
}

if [ "$IS_PULL_REQUEST" != true ]; then
    run_tests
    copy_test_reports
    run_build
else
    echo "Skipping, it's a PR"
fi