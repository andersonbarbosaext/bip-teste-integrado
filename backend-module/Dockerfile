###############################################
# STAGE 1 — BUILD (WAR EXECUTÁVEL)
###############################################
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn -e -T1C -DskipTests package


###############################################
# STAGE 2 — RUNTIME
###############################################
FROM eclipse-temurin:17-jdk AS runtime

WORKDIR /app

COPY --from=builder /app/target/*.war app.war

ENTRYPOINT ["java", "-jar", "app.war"]