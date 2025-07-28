#!/bin/bash

# 현재 디렉터리 기준으로 작업
REPOSITORY=/home/ec2-user
cd $REPOSITORY

CONTAINER_NAME="birthday-hansang-server"
IMAGE_NAME="birthday-hansang-image"
SPRING_PROFILE="prod"

echo "> 이전 컨테이너 종료 및 제거"
docker stop $CONTAINER_NAME || true
docker rm $CONTAINER_NAME || true

echo "> 기존 이미지 제거"
docker rmi $IMAGE_NAME || true

echo "> Docker 이미지 빌드 시작"
docker build -t $IMAGE_NAME .

echo "> 컨테이너 실행 시작"
docker run -d \
  --name $CONTAINER_NAME \
  -p 8081:8081 \
  --restart always \
  -v /etc/localtime:/etc/localtime:ro \
  -e TZ=Asia/Seoul \
  $IMAGE_NAME \
  --spring.profiles.active=$SPRING_PROFILE
