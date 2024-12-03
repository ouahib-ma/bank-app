# Build stage
FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Run stage
FROM openjdk:17-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar message-service.jar

# Install curl for healthcheck
RUN apk add --no-cache curl

ENV JAVA_OPTS="-Xms256m -Xmx512m"
EXPOSE 8081
HEALTHCHECK --interval=30s --timeout=3s --retries=3 \
    CMD curl -f http://localhost:8081/api/actuator/health || exit 1

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar message-service.jar"]