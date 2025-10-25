# --- СТАДИЯ 1: Сборка (Build Stage) ---
# Используем официальный образ Maven. Он уже содержит JDK (мы используем OpenJDK 25)
# и Maven.
FROM maven:3.9.5-eclipse-temurin-25 AS builder

# Установите рабочую директорию внутри контейнера
WORKDIR /app

# Скопируйте файлы сборки (pom.xml)
# Используем COPY --chown для обеспечения правильных прав доступа
COPY --chown=maven:maven pom.xml .

# Скопируйте исходный код
COPY --chown=maven:maven src /app/src

# Переключаемся на пользователя Maven, чтобы не выполнять сборку от root
USER maven

# Соберите приложение в JAR-файл
RUN mvn clean package -DskipTests

# ----------------------------------------------------------------------
# --- СТАДИЯ 2: Запуск (Runtime Stage) ---
# Используем минимальный образ JRE 25 для уменьшения размера и повышения безопасности
FROM eclipse-temurin:25-jre-jammy

# Устанавливаем переменную окружения для порта (Render ожидает, что приложение использует PORT)
ENV PORT 8080

# Открываем порт
EXPOSE 8080

# Копируем JAR-файл из стадии сборки.
# Maven помещает файл в /app/target/
ARG JAR_FILE=/app/target/*.jar
COPY --from=builder ${JAR_FILE} app.jar

# Команда для запуска JAR-файла
ENTRYPOINT ["java", "-jar", "/app.jar"]