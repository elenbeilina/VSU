FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

ENV app_port=- \
    server_oracle=- \
    server_oracle_port=- \
    oracle_db_name=- \
    oracle_db_user=- \
    oracle_db_password=-

ENTRYPOINT ["java","-jar","/app.jar"]