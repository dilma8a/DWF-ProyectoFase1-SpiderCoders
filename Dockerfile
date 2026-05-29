# ── Stage 1: Build WAR with Maven ──────────────────────────────────────────
FROM maven:3.9-eclipse-temurin-11-alpine AS builder

WORKDIR /app

# Cache de dependencias separado del código fuente
COPY pom.xml .
RUN mvn dependency:go-offline -q

COPY src ./src
RUN mvn package -DskipTests -q

# ── Stage 2: Runtime con Tomcat 9 ──────────────────────────────────────────
FROM tomcat:9.0-jre11

# Limpiar webapps por defecto de Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Desplegar como ROOT para que la app quede en /
COPY --from=builder /app/target/DWF-ProyectoFase1-SpiderCoders.war \
     /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080

HEALTHCHECK --interval=20s --timeout=10s --start-period=60s --retries=3 \
  CMD wget -qO- http://localhost:8080/login.xhtml || exit 1

CMD ["catalina.sh", "run"]
