FROM eclipse-temurin:21-jre AS runtime
LABEL org.opencontainers.image.source="https://github.com/spolka-zlo/d17map-backend"

WORKDIR /app

COPY build/libs/*.jar app.jar
ENV SPRING_PROFILES_ACTIVE=prod

EXPOSE 8080


ENTRYPOINT ["java", "-jar", "/app/app.jar"]
