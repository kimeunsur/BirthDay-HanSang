# Dockerfile.dev : CI 빌드용

# 1단계: Gradle 빌드 단계
FROM gradle:8.4.0-jdk17 AS builder
WORKDIR /app

# Gradle 캐시 최적화
COPY build.gradle settings.gradle ./
COPY gradle ./gradle
RUN gradle build -x test || return 0

# 전체 소스 복사 후 빌드
COPY . .
RUN gradle clean build -x test

# 2단계: 실행용 이미지
FROM amazoncorretto:17
WORKDIR /app

# jar 복사
COPY --from=builder /app/build/libs/*.jar app.jar

# EC2에서는 application.yml 따로 쓰므로 포함 X
ENTRYPOINT ["java", "-jar", "app.jar"]
