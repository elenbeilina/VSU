#!/bin/sh

mvn clean package

docker build -t github.com/elenbeilina/vsu/vsu:latest .
docker push github.com/elenbeilina/vsu/vsu:latest

#пример упаковки в архив и восстановления
#docker build -t vsu:latest .
#docker save -o vsu-latest.tar vsu:latest
#docker load -i vsu-latest.tar vsu:latest