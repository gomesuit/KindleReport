#!/bin/sh

if [ $# -ne 1 ]; then
  echo "Required single argument" 1>&2
  echo "Usage: build.sh {release,develop}" 1>&2
  exit 1
fi

MAVEN_PROFILE=$1

echo "MAVEN_PROFILE = -P$MAVEN_PROFILE"

#mvn clean package -Dmaven.test.skip=true
docker run --rm -v `pwd`:/app -w /app --volumes-from mavencache maven:3-jdk-7 mvn clean package -P$MAVEN_PROFILE -Dmaven.test.skip=true

sudo cp target/KindleReport-0.0.1-SNAPSHOT.jar target/app.jar
