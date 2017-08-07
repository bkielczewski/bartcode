FROM java:8

EXPOSE 8080

VOLUME /app/logs

COPY ./target/bartcode-service-*.jar /app/app.jar

WORKDIR /app
RUN bash -c 'touch ./app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","./app.jar"]
