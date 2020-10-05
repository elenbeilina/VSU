#!/bin/sh

mvn clean package

docker build -t elenbeilina/vsu-service:vsu-latest .
docker push elenbeilina/vsu-service:vsu-latest

#пример упаковки в архив и восстановления
#docker build -t vsu:latest .
#docker save -o vsu-latest.tar vsu:latest
#docker load -i vsu-latest.tar vsu:latest