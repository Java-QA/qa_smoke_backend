# WishList API

REST API для управления списками желаний с возможностью добавления подарков и отслеживания их статуса.

## Технологии

- Java 22
- Spring Boot
- PostgreSQL
- Spring Security с JWT аутентификацией
- OpenAPI/Swagger документация
- Docker

## Требования

- Docker и Docker Compose
- Java 22 (для локальной разработки)
- Maven (для локальной разработки)

## Запуск приложения

### Через Docker Compose

1. Склонируйте репозиторий
2. Перейдите в директорию проекта
3. Запустите приложение:
```bash
docker-compose up -d
```

Приложение будет доступно по адресу: http://localhost:8080

### Локальный запуск для разработки

1. Установите PostgreSQL и создайте базу данных:
```sql
CREATE DATABASE wishlist;
```

2. Настройте переменные окружения:
```bash
export POSTGRES_URL=jdbc:postgresql://localhost:5432/wishlist
export POSTGRES_USER=your_username
export POSTGRES_PASSWORD=your_password
export JWT_SECRET=your_jwt_secret
```

3. Запустите приложение:
```bash
./mvnw spring-boot:run
```

## API Документация

После запуска приложения, Swagger UI доступен по адресу:
http://localhost:8080/swagger-ui.html

## Основные эндпоинты

### Аутентификация

- POST /api/auth/register - Регистрация нового пользователя
- POST /api/auth/login - Авторизация пользователя

### Списки желаний

- GET /api/wishlists - Получение всех списков желаний пользователя
- POST /api/wishlists - Создание нового списка желаний
- GET /api/wishlists/{id} - Получение списка желаний по ID
- PUT /api/wishlists/{id} - Обновление списка желаний
- DELETE /api/wishlists/{id} - Удаление списка желаний

### Подарки

- GET /api/wishlists/{wishListId}/gifts - Получение всех подарков в списке желаний
- POST /api/wishlists/{wishListId}/gifts - Добавление нового подарка
- GET /api/wishlists/{wishListId}/gifts/{id} - Получение подарка по ID
- PUT /api/wishlists/{wishListId}/gifts/{id} - Обновление подарка
- DELETE /api/wishlists/{wishListId}/gifts/{id} - Удаление подарка
- POST /api/wishlists/{wishListId}/gifts/{id}/purchase - Пометка подарка как купленного

## Тестирование

Для запуска тестов выполните:
```bash
./mvnw test
```

## Переменные окружения

- `POSTGRES_URL` - URL подключения к базе данных
- `POSTGRES_USER` - Имя пользователя базы данных
- `POSTGRES_PASSWORD` - Пароль базы данных
- `JWT_SECRET` - Секретный ключ для JWT токенов
