FROM gradle:8.14-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle :file-service:bootJar --no-daemon

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/file-service/build/libs/*.jar app.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "app.jar"]