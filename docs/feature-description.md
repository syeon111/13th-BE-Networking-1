# 배포 과제 - 구성 설명

## 1. 아키텍처 다이어그램

```
사용자 (Browser)
      ↓ HTTPS
3.38.149.57.nip.io
      ↓
  Nginx (port 443/80)
  - HTTP → HTTPS 리다이렉트
  - 리버스 프록시
      ↓
Spring Boot 컨테이너 (port 8080)
      ↓
MySQL 컨테이너 (port 3306)
      ↑
   Docker Volume (데이터 영속성)

---CI/CD 흐름---

GitHub (develop push)
      ↓
GitHub Actions
  - Docker 이미지 빌드
  - Docker Hub push
      ↓
EC2 SSH 접속
  - 새 이미지 pull
  - 기존 컨테이너 교체
```

## 2. 배포 URL

```
https://3.38.149.57.nip.io
```

Swagger 접속 URL:

```
https://3.38.149.57.nip.io/swagger-ui/index.html
```

## 3. 배포된 Swagger 접속 화면 캡처

> 캡처 첨부 예정

## 4. GitHub Actions 성공 화면 캡처

> 캡처 첨부 예정

## 5. Dockerfile / Nginx 설정 내용

### Dockerfile

멀티스테이지 빌드 방식을 사용했습니다.

- **Stage 1 (builder)**: `gradle:8.13-jdk17` 이미지에서 `./gradlew bootJar`로 JAR 빌드
- **Stage 2 (run)**: `eclipse-temurin:17-jre` 경량 이미지에서 JAR 실행
- `-Dspring.profiles.active=prod`로 운영 프로필 활성화

### Nginx 설정

```nginx
server {
    listen 80;
    server_name 3.38.149.57.nip.io;

    location / {
        proxy_pass http://127.0.0.1:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
}
```

Certbot 적용 후 HTTPS 리다이렉트가 자동으로 추가됩니다.

### 이미지 배포 방식

GitHub Actions에서 Docker Hub로 이미지를 push하고, EC2에서 pull하는 방식을 사용했습니다.

1. `develop` 브랜치 push → GitHub Actions 트리거
2. `docker/build-push-action`으로 이미지 빌드 및 Docker Hub push
3. `appleboy/ssh-action`으로 EC2에 SSH 접속
4. EC2에서 새 이미지 pull 후 컨테이너 교체

## 6. 트러블슈팅 노트

### 문제 1: docker-compose 명령어 실행 불가

```
문제: Ubuntu 24.04에서 docker-compose-plugin 패키지를 찾을 수 없음

원인: Ubuntu 24.04의 기본 apt 저장소에 docker-compose-plugin이 없음

해결: docker.io 패키지로 Docker 설치 후, GitHub Releases에서
      docker-compose 바이너리를 직접 다운로드하여 /usr/local/bin에 설치
```

### 문제 2: EC2에서 컨테이너 실행 시 플랫폼 불일치

```
문제: M1/M2 Mac에서 빌드한 이미지가 EC2(amd64)에서 실행되지 않음

원인: Mac에서 기본 빌드 시 ARM64 아키텍처 이미지가 생성됨

해결: docker buildx build --platform linux/amd64 옵션으로 재빌드 후 push
```

### 문제 3: MySQL 권한 오류로 앱 컨테이너 반복 재시작

```
문제: Access denied for user 'cotato'@'%' to database 'cotato'

원인: MySQL 컨테이너 초기화 시 유저 생성은 됐으나 DB 권한이 부여되지 않음

해결: docker exec으로 MySQL 접속 후 GRANT ALL PRIVILEGES ON cotato.* TO 'cotato'@'%' 실행
```
