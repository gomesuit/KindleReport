#!/bin/sh

docker stop kindlereport
docker rm kindlereport
docker run -d \
	--name kindlereport \
	-e JAVA_OPTS="-Xms192m -Xmx192m -XX:MaxPermSize=64m" \
	-e LANG=en_US.UTF-8 \
	--link mysql:mysql-server \
	kindlereport
