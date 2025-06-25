# Etapa de build com Maven
FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder

WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

# Etapa final com JDK e Dockerize
FROM eclipse-temurin:21-jdk-alpine

# Instala wget e adiciona o Dockerize
RUN apk add --no-cache wget ca-certificates \
  && wget -O dockerize.tar.gz https://github.com/jwilder/dockerize/releases/download/v0.7.0/dockerize-linux-amd64-v0.7.0.tar.gz \
  && tar -C /usr/local/bin -xzvf dockerize.tar.gz \
  && rm dockerize.tar.gz

WORKDIR /app

# Copia o JAR do builder
COPY --from=builder /app/target/cafeteria-*.jar app.jar

# Usa dockerize para aguardar o banco antes de iniciar
CMD ["dockerize", "-wait", "tcp://db:5432", "-timeout", "60s", "java", "-jar", "app.jar"]
