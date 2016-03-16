FROM java:7

ADD ./target/app.jar /app/app.jar

ENV LANG en_US.UTF-8

# set timezone
RUN cp -f /usr/share/zoneinfo/Asia/Tokyo /etc/localtime

EXPOSE 8089

CMD java -jar -Duser.timezone=Asia/Tokyo -Dfile.encoding=UTF-8 $JAVA_OPTS /app/app.jar
