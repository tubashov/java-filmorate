# java-filmorate
Template repository for Filmorate project.

# Filmorate

## Схема базы данных
![Схема БД](docs/schema.png)

Полный файл диаграммы в формате dbml: [filmorate.dbml](docs/filmorate.dbml)

### Пояснения
- Таблица `app_users` хранит информацию о пользователях.
- Таблица `films` хранит информацию о фильмах и содержит ссылку на рейтинг MPA.
- Таблица `genres` и промежуточная таблица `film_genres` реализуют связь "многие-ко-многим".
- Таблица `film_likes` позволяет учитывать лайки фильмов пользователями.
- Таблица `friendships` реализует "дружбу" между пользователями с возможностью статуса (PENDING, CONFIRMED).
