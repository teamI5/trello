#!/bin/bash

echo "----------서버 시작------------"
docker stop trello-server || true
docker rm trello-server || true
docker pull 767397671057.dkr.ecr.ap-northeast-2.amazonaws.com/trello-server:latest
docker run -d --name trello-server -p 80:8080 767397671057.dkr.ecr.ap-northeast-2.amazonaws.com/trello-server:latest
echo "----------서버 배포 끝---------"