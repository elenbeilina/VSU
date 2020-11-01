FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

ENV app_port=- \
    server_postgres=-

ENTRYPOINT ["java","-jar","/app.jar","--server.port=${app_port}","--server.postrgres=${server_postgres}"]