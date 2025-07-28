#!/bin/bash

REPOSITORY=/home/ec2-user/birthday-hansang
cd $REPOSITORY

CONTAINER_NAME="birthday-hansang-server"
IMAGE_NAME="birthday-hansang-image"
JAR_NAME=$(ls $REPOSITORY/build/libs/*.jar | grep -v plain | head -n 1)
SPRING_PROFILE="prod"

echo "> 기존 컨테이너 중지 및 삭제"
docker stop $CONTAINER_NAME 2>/dev/null || true
docker rm $CONTAINER_NAME 2>/dev/null || true

echo "> 기존 이미지 삭제"
docker rmi $IMAGE_NAME 2>/dev/null || true

echo "> Dockerfile 기준 이미지 빌드"
docker build -t $IMAGE_NAME .

echo "> Docker 컨테이너 실행"
docker run -d \
  --name $CONTAINER_NAME \
  -p 8081:8081 \
  --restart always \
  -v /etc/localtime:/etc/localtime:ro \
  -e TZ=Asia/Seoul \
  $IMAGE_NAME \
  --spring.profiles.active=$SPRING_PROFILE
