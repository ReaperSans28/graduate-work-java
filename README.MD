# Marketplace Backend API

## Описание проекта
Backend-часть маркетплейса, разработанная на Java с использованием Spring Boot. Приложение предоставляет REST API для управления объявлениями, комментариями и пользователями с системой аутентификации и авторизации. Проект реализован в соответствии с OpenAPI-спецификацией и следует принципам RESTful архитектуры.

## Функциональность

### Управление пользователями
- Регистрация и аутентификация пользователей
- Смена пароля
- Ролевая модель (USER, ADMIN)

### Управление объявлениями
- Просмотр всех объявлений (доступно без авторизации)
- Получение объявления по ID
- Создание новых объявлений
- Редактирование собственных объявлений
- Удаление объявлений

### Управление комментариями
- Просмотр комментариев к объявлениям
- Добавление комментариев
- Редактирование собственных комментариев
- Удаление комментариев

### Безопасность
- Basic-аутентификация
- Авторизация на основе ролей
- Защита от несанкционированного доступа к чужим данным
- ADMIN имеет права на управление всеми объявлениями и комментариями

## Технологии
- **Java 17+**
- **Spring Boot 2.7+**
- **Spring Security** - аутентификация и авторизация
- **Spring Data JPA** - работа с базой данных
- **PostgreSQL** - реляционная база данных
- **Liquibase** - управление миграциями базы данных
- **MapStruct** - маппинг между DTO и Entity
- **Springdoc OpenAPI** - документация API (Swagger UI)
- **JUnit 5, Mockito** - тестирование
- **Maven** - управление зависимостями

## Архитектура
Проект следует паттерну **MVC** с четким разделением ответственности:

### Структура пакетов
src/main/java/com/marketplace/
├── config/ # Конфигурационные классы
├── controller/ # REST контроллеры
├── dto/ # Data Transfer Objects
├── entity/ # JPA сущности
├── mapper/ # Мапперы (MapStruct)
├── repository/ # Spring Data репозитории
├── service/ # Бизнес-логика
└── security/ # Конфигурация безопасности


### Ключевые компоненты
1. **Контроллеры** (`controller/`)
    - Обрабатывают HTTP-запросы
    - Возвращают соответствующие DTO
    - Используют аннотации Swagger для документации

2. **Сервисы** (`service/`)
    - Содержат бизнес-логику
    - Используют репозитории для доступа к данным
    - Обрабатывают маппинг через MapStruct

3. **Репоизитории** (`repository/`)
    - Интерфейсы Spring Data JPA
    - Обеспечивают доступ к базе данных

4. **Сущности** (`entity/`)
    - Пользователь (User)
    - Объявление (Ad)
    - Комментарий (Comment)

5. **DTO** (`dto/`)
    - Объекты для передачи данных
    - Отделены от сущностей БД

## Требования к окружению
- JDK 17 или выше
- Maven 3.6+
- PostgreSQL 15+
- Docker

## Установка и запуск

### 1. Клонирование репозитория
```bash
git clone https://github.com/ReaperSans28/graduate-work-java.git
cd marketplace-backend
```
### 2. Настройка базы данных
```CREATE DATABASE marketplace;
CREATE USER marketplace_user WITH PASSWORD 'password';
GRANT ALL PRIVILEGES ON DATABASE marketplace TO marketplace_user;
```
### 3. Настройка конфигурации
Создайте application.properties или используйте application.yml:
```
spring.datasource.url=jdbc:postgresql://localhost:5432/YOUR_DATABASE
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false
spring.jpa.open-in-view=false
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
spring.liquibase.change-log=classpath:liquibase/changelog-master.yml
spring.servlet.multipart.max-file-size=5MB
spring.servlet.multipart.max-request-size=5MB
server.port=8080
```
### 4. Сборка и запуск
```
mvn clean install
cd ...\project; $env:JAVA_HOME="C:\Users\user\.jdks\openjdk-24"; .\mvnw spring-boot:run
```
Приложение будет доступно по адресу: http://localhost:8080

## Тестирование
Проект включает полное покрытие тестами:
- Юнит-тесты для сервисов
- Интеграционные тесты для контроллеров
- Тесты безопасности

### Для запуска тестов:
```
mvn test
```

## Документация API
После запуска приложения документация API доступна через:
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI спецификация: http://localhost:8080/v3/api-docs