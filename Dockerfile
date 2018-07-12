FROM openjdk:alpine

RUN mkdir -p /temp-file-viewer/app

COPY temp-file-viewer-backend/target/freehub-file-viewer.jar /temp-file-viewer/app/freehub-file-viewer.jar

WORKDIR /temp-file-viewer/app/

ENV JAVA_OPTS='-server'
ENV APP_OPTS=''

EXPOSE 8081

ENTRYPOINT java ${JAVA_OPTS} ${APP_OPTS} -jar /temp-file-viewer/app/freehub-file-viewer.jar

