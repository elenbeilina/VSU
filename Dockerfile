# select parent image
FROM maven:3.6.3-jdk-8

# copy the source tree and the pom.xml to our new container
COPY ./ ./

# package our application code
RUN mvn clean package

ENV app_port=- \
    server_postgres=-

ENTRYPOINT ["java","-jar","/app.jar","--server.port=${app_port}","--server.postrgres=${server_postgres}"]