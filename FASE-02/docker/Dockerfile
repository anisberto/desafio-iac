# =============================================================================
# Stage 1: Build
# =============================================================================
FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /app

COPY Desafio-IaC/mvnw Desafio-IaC/.mvn/ ./
COPY Desafio-IaC/pom.xml ./
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

COPY Desafio-IaC/src ./src
RUN ./mvnw package -DskipTests -B

# =============================================================================
# Stage 2: Runtime
# =============================================================================
FROM eclipse-temurin:21-jre-alpine AS runtime

RUN addgroup -S appgroup && adduser -S appuser -G appgroup

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

RUN chown -R appuser:appgroup /app
USER appuser

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=5s --start-period=60s --retries=3 \
  CMD wget -qO- http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]
