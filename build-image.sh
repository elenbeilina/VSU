#!/bin/sh

mvn clean package

docker build -t elenbeilina/vsu-service:1 .
docker push elenbeilina/vsu-service:1

#пример упаковки в архив и восстановления
#docker build -t vsu:latest .
#docker save -o vsu-latest.tar vsu:latest
#docker load -i vsu-latest.tar vsu:latest