#!/bin/bash

REPOSITORY=/home/ubuntu/birthday-hansang
cd $REPOSITORY

CONTAINER_NAME="birthday-hansang-server"
IMAGE_NAME="birthday-hansang-image"
SPRING_PROFILE="prod"  # dev

echo "> 이전 컨테이너 종료 및 제거"
docker stop $CONTAINER_NAME || true
docker rm $CONTAINER_NAME || true

echo "> 기존 이미지 제거"
docker rmi $IMAGE_NAME || true

echo "> Docker 이미지 빌드 시작"
docker build -t $IMAGE_NAME .
docker run -d --name $CONTAINER_NAME ... $IMAGE_NAME

echo "> 컨테이너 실행 시작"
docker run -d \
  --name $CONTAINER_NAME \
  -p 8081:8081 \
  --restart always \
  -v /etc/localtime:/etc/localtime:ro \
  -e TZ=Asia/Seoul \
  $IMAGE_NAME \
  --spring.profiles.active=$SPRING_PROFILE