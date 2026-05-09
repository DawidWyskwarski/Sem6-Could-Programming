FROM gradle:8.14-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle :track-service:bootJar --no-daemon

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/track-service/build/libs/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]