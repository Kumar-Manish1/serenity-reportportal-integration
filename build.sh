#!/bin/bash

# Build script used by Travis CI

set -ev
echo "Active branch: $1"

case "$1" in
"develop")
    echo "Snapshot publisher will be applied"
    mvn clean deploy
    ;;
"master")
    echo "Processing as Master"
    echo "Build and publish release"
    ;;
*)
    echo "PR builder will be applied"
#    mvn clean package
    mvn --settings custom-settings.xml clean deploy
    ;;
esac
