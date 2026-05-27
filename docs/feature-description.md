# 배포 과제 - 구성 설명

## 1. 아키텍처 다이어그램

```
사용자 (Browser)
      ↓ HTTPS
<EC2_PUBLIC_IP>.nip.io
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

GitHub (main push)
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

> EC2 퍼블릭 IP 확정 후 작성 예정

```
https://<EC2_PUBLIC_IP>.nip.io
```

Swagger 접속 URL:

```
https://<EC2_PUBLIC_IP>.nip.io/swagger-ui/index.html
```

## 3. 배포된 Swagger 접속 화면 캡처

> 배포 완료 후 캡처 첨부 예정

## 4. GitHub Actions 성공 화면 캡처

> 배포 완료 후 캡처 첨부 예정

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
    server_name <EC2_PUBLIC_IP>.nip.io;

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

1. `main` 브랜치 push → GitHub Actions 트리거
2. `docker/build-push-action`으로 이미지 빌드 및 Docker Hub push
3. `appleboy/ssh-action`으로 EC2에 SSH 접속
4. EC2에서 새 이미지 pull 후 컨테이너 교체

## 6. 트러블슈팅 노트

> 배포 과정에서 발생한 문제는 여기에 기록합니다.

---

### 예시 형식

```
문제:

원인:

해결:
```
