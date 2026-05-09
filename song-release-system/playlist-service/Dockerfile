FROM gradle:8.14-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle :playlist-service:bootJar --no-daemon

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/playlist-service/build/libs/*.jar app.jar

EXPOSE 8084

ENTRYPOINT ["java", "-jar", "app.jar"]