# WishList API

REST API для управления списками желаний с возможностью добавления подарков и отслеживания их статуса.

## Технологии

- Java 17
- Spring Boot 3.2.2
- PostgreSQL 16
- Spring Security с JWT аутентификацией
- OpenAPI/Swagger документация
- Docker и Docker Compose
- Maven

## Требования

- Docker и Docker Compose
- Java 17 (для локальной разработки)
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
База данных будет доступна по адресу: localhost:5432

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

## Управление контейнерами

### Экспорт контейнера в TAR архив:
```bash
docker save -o wishlist-app.tar wishlist-app
```

### Импорт на другой машине:
```bash
docker load -i wishlist-app.tar
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

## Безопасность

- Аутентификация на основе JWT токенов
- Использование UUID для идентификаторов
- Безопасное хранение паролей
- Контроль доступа на основе ролей

## Структура базы данных

Основные сущности:
- Пользователи (Users)
- Списки желаний (WishLists)
- Подарки (Gifts)

Все сущности используют UUID в качестве первичного ключа для повышенной безопасности.

## Переменные окружения

Настройте следующие переменные в `docker-compose.yml`:

```yaml
POSTGRES_URL=jdbc:postgresql://db:5432/wishlist
POSTGRES_USER=wishlist
POSTGRES_PASSWORD=wishlist
JWT_SECRET=your_jwt_secret_key_here_please_change_in_production
```

### Описание переменных:
- `POSTGRES_URL` - URL подключения к базе данных
- `POSTGRES_USER` - Имя пользователя базы данных
- `POSTGRES_PASSWORD` - Пароль базы данных
- `JWT_SECRET` - Секретный ключ для JWT токенов
