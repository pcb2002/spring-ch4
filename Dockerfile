# 1. Java 17 실행 환경을 가져옵니다.
FROM eclipse-temurin:17-jdk-slim

# 2. 컨테이너 내부에 작업 디렉토리를 설정합니다.
WORKDIR /app

# 3. 빌드된 jar 파일을 컨테이너의 app.jar로 복사합니다.
COPY build/libs/*-SNAPSHOT.jar app.jar

# 4. 8080 포트를 열어줍니다.
EXPOSE 8080

# 5. 컨테이너가 켜질 때 실행할 명령어를 지정합니다.
ENTRYPOINT ["java", "-jar", "app.jar"]