FROM openjdk:8-jre

EXPOSE 8080

VOLUME /app/logs

COPY ./build/libs/*.jar /app/app.jar

WORKDIR /app

ENTRYPOINT ["java","-jar","./app.jar"]