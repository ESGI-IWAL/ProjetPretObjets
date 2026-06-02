# Multi-stage Dockerfile for building and running the Spring Boot backend
# Stage 1: build with Maven
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /build

# Copy pom and source then build the app (skip tests to speed up builds)
COPY pom.xml ./
COPY src ./src
ARG MAVEN_OPTS=""
# allow skipping tests at build time: docker build --build-arg SKIP_TESTS=true
ARG SKIP_TESTS=false
RUN mvn -B ${MAVEN_OPTS} package $(if [ "$SKIP_TESTS" = "true" ]; then echo -DskipTests; fi)

# Stage 2: runtime image with JRE
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy the fat jar produced by the builder
COPY --from=builder /build/target/pretemoica-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

# Runtime configuration
# SPRING_PROFILES_ACTIVE can be set at runtime to select Spring profile (dev/prod/...)
ENV SPRING_PROFILES_ACTIVE=prod
# Allow passing extra java options with JAVA_OPTS
ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dspring.profiles.active=$SPRING_PROFILES_ACTIVE -jar /app/app.jar"]

