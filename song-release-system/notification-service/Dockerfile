FROM gradle:8.14-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle :notification-service:bootJar --no-daemon

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/notification-service/build/libs/*.jar app.jar

EXPOSE 8085

ENTRYPOINT ["java", "-jar", "app.jar"]