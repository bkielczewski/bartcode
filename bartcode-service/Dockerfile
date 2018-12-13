FROM openjdk:8-jre-alpine

EXPOSE 8080

VOLUME /app/logs

COPY ./build/libs/*.jar /app/app.jar

WORKDIR /app

RUN apk --no-cache add curl

HEALTHCHECK CMD curl --fail http://localhost:8080/api/actuator/health || exit 1

ENTRYPOINT ["java","-jar","./app.jar"]
