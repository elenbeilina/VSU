FROM openjdk:8-jdk-alpine
COPY target/*.jar app.jar

ENV app_port=- \
    server_postgres=-

ENTRYPOINT ["java","-jar","/app.jar","--server.port=${app_port}","--server.postrgres=${server_postgres}"]