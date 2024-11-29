# Используем базовый образ с OpenJDK для запуска Java-приложений
FROM eclipse-temurin:17-jdk-alpine

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем сборку jar файла из target директории в контейнер
COPY target/wishlist-1.0-SNAPSHOT.jar /app/app.jar

# Указываем команду запуска приложения
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

# Определите порт, который ваше приложение будет использовать
EXPOSE 8080
