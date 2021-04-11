FROM frolvlad/alpine-oraclejdk8:slim
WORKDIR /root
ADD iHelin-2.0.0.jar app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""
ENV ARGS=""

EXPOSE 8888
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar app.jar --spring.profiles.active=prod $ARGS" ]