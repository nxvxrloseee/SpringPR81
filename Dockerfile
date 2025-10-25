# --- СТАДИЯ 1: Сборка (Build Stage) ---

FROM eclipse-temurin:25-jdk-jammy AS builder


WORKDIR /app


COPY pom.xml .
COPY settings.xml .

COPY src /app/src

# Соберите приложение в JAR-файл
# -DskipTests: пропускаем тесты для ускорения сборки
RUN mvn clean package -DskipTests

# --- СТАДИЯ 2: Запуск (Runtime Stage) ---
FROM eclipse-temurin:25-jre-jammy


ENV PORT 8080

EXPOSE 8080


ARG JAR_FILE=/app/target/*.jar
COPY --from=builder ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]