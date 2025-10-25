# --- СТАДИЯ 1: Сборка (Build Stage) ---
# Используем ваш исходный образ OpenJDK 25.
FROM eclipse-temurin:25-jdk-jammy AS builder

# Установим Maven вручную, так как готового тега нет.
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
# Теперь команда 'mvn' будет найдена
RUN mvn clean package -DskipTests

# ----------------------------------------------------------------------
# --- СТАДИЯ 2: Запуск (Runtime Stage) ---
# Используем минимальный образ JRE 25
FROM eclipse-temurin:25-jre-jammy

# Устанавливаем переменные окружения
ENV PORT 8080
EXPOSE 8080

# Копируем JAR-файл из стадии сборки
ARG JAR_FILE=/app/target/*.jar
COPY --from=builder ${JAR_FILE} app.jar

# Команда для запуска JAR-файла
ENTRYPOINT ["java", "-jar", "/app.jar"]