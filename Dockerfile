# syntax=docker/dockerfile:1
FROM openjdk:11
LABEL maintainer="arthurstrokov@gmail.com"
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=build/libs/resume-storage-project-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]