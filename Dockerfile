# --- СТАДИЯ 1: Сборка (Build Stage) ---.
FROM eclipse-temurin:25-jdk-jammy AS builder

# Установим Maven вручную.
# Обновляем список пакетов и устанавливаем Maven.
RUN apt-get update && \
    apt-get install -y maven && \
    rm -rf /var/lib/apt/lists/*

# Устанавливаем рабочую директорию
WORKDIR /app

# Скопируйте файлы сборки (pom.xml) и исходный код
COPY pom.xml .
COPY src /app/src

# Соберите приложение в JAR-файл
RUN mvn clean package -DskipTests

# ----------------------------------------------------------------------
# --- СТАДИЯ 2: Запуск (Runtime Stage) ---
FROM eclipse-temurin:25-jre-jammy

# Устанавливаем переменные окружения
ENV PORT 8080
EXPOSE 8080

# Копируем JAR-файл из стадии сборки
ARG JAR_FILE=/app/target/*.jar
COPY --from=builder ${JAR_FILE} app.jar

# Команда для запуска JAR-файла
ENTRYPOINT ["java", "-jar", "/app.jar"]