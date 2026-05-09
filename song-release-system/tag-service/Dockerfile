FROM gradle:8.14-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle :tag-service:bootJar --no-daemon

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/tag-service/build/libs/*.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]