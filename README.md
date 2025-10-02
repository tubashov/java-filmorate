# java-filmorate
Template repository for Filmorate project.

# Filmorate

## Схема базы данных
![Схема БД](docs/schema.png)

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 173cf76 (Update .gitignore)
Полный файл диаграммы в формате dbml: [filmorate.dbml](docs/filmorate.dbml)

### Пояснения
- Таблица `users` хранит информацию о пользователях.
- Таблица `films` хранит информацию о фильмах и содержит ссылку на рейтинг MPA.
- Таблица `genres` и промежуточная таблица `film_genres` реализуют связь "многие-ко-многим".
- Таблица `likes` позволяет учитывать лайки фильмов пользователями.
- Таблица `friendships` реализует "дружбу" между пользователями с возможностью статуса (PENDING, CONFIRMED).


## Примеры SQL-запросов

### Получить все фильмы
```sql
SELECT * 
FROM films;
```

### Получить всех пользователей
```sql
SELECT * 
FROM users;
```

### Найти топ-5 самых популярных фильмов (по количеству лайков)
```sql
SELECT f.id, f.name, COUNT(l.user_id) AS likes_count
FROM films f
LEFT JOIN likes l ON f.id = l.film_id
GROUP BY f.id, f.name
ORDER BY likes_count DESC
LIMIT 5;
```

### Получить список друзей пользователя
```sql
SELECT u.id, u.name, fs.status
FROM friendships fs
JOIN users u ON fs.friend_id = u.id
WHERE fs.user_id = 1;
```

### Получить общих друзей двух пользователей
```sql
SELECT u.id, u.name
FROM friendships fs1
JOIN friendships fs2 ON fs1.friend_id = fs2.friend_id
JOIN users u ON fs1.friend_id = u.id
WHERE fs1.user_id = 1 
  AND fs2.user_id = 2
  AND fs1.status = 'CONFIRMED'
  AND fs2.status = 'CONFIRMED';
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> dfb8514 (Исправление ошибок.)
=======
>>>>>>> 173cf76 (Update .gitignore)
```

# Filmorate

## Схема базы данных
![Схема БД](docs/schema.png)

<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> a98b57d (Migrate clean changes from add-friends-likes excluding ignored/binary files)
=======
>>>>>>> dfb8514 (Исправление ошибок.)
=======
>>>>>>> 173cf76 (Update .gitignore)
Полный файл диаграммы в формате dbml: [filmorate.dbml](docs/filmorate.dbml)

### Пояснения
- Таблица `users` хранит информацию о пользователях.
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> a98b57d (Migrate clean changes from add-friends-likes excluding ignored/binary files)
=======
>>>>>>> dfb8514 (Исправление ошибок.)
=======
>>>>>>> 173cf76 (Update .gitignore)
- Таблица `films` хранит информацию о фильмах и содержит ссылку на рейтинг MPA.
- Таблица `genres` и промежуточная таблица `film_genres` реализуют связь "многие-ко-многим".
- Таблица `likes` позволяет учитывать лайки фильмов пользователями.
- Таблица `friendships` реализует "дружбу" между пользователями с возможностью статуса (PENDING, CONFIRMED).


## Примеры SQL-запросов

### Получить все фильмы
```sql
SELECT * 
FROM films;
```

### Получить всех пользователей
```sql
SELECT * 
FROM users;
```

### Найти топ-5 самых популярных фильмов (по количеству лайков)
```sql
SELECT f.id, f.name, COUNT(l.user_id) AS likes_count
FROM films f
LEFT JOIN likes l ON f.id = l.film_id
GROUP BY f.id, f.name
ORDER BY likes_count DESC
LIMIT 5;
```

### Получить список друзей пользователя
```sql
SELECT u.id, u.name, fs.status
FROM friendships fs
JOIN users u ON fs.friend_id = u.id
WHERE fs.user_id = 1;
```

### Получить общих друзей двух пользователей
```sql
SELECT u.id, u.name
FROM friendships fs1
JOIN friendships fs2 ON fs1.friend_id = fs2.friend_id
JOIN users u ON fs1.friend_id = u.id
WHERE fs1.user_id = 1 
  AND fs2.user_id = 2
  AND fs1.status = 'CONFIRMED'
  AND fs2.status = 'CONFIRMED';
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 21930b7 (Исправление ошибок.)
```
=======
Полный файл диаграммы в формате dbml: [schema.dbml](docs/schema.dbml)
=======
Полный файл диаграммы в формате dbml: [filmorate-db.dbml](docs/schema.dbml)
>>>>>>> 233fa4a (Изменение README.md.)
=======
Полный файл диаграммы в формате dbml: [filmorate-db.dbml](docs/filmorate-db.dbml)
>>>>>>> fa93aae (Изменение README.md.)
=======
Полный файл диаграммы в формате dbml: [filmorate.dbml](docs/filmorate.dbml)
>>>>>>> 7a2046f (Добавление filmorate.dbml.)

### Пояснения
- Таблица `app_users` хранит информацию о пользователях.
=======
>>>>>>> f53bbc2 (Изменение README.md.)
- Таблица `films` хранит информацию о фильмах и содержит ссылку на рейтинг MPA.
- Таблица `genres` и промежуточная таблица `film_genres` реализуют связь "многие-ко-многим".
- Таблица `likes` позволяет учитывать лайки фильмов пользователями.
- Таблица `friendships` реализует "дружбу" между пользователями с возможностью статуса (PENDING, CONFIRMED).
<<<<<<< HEAD
>>>>>>> 17cbdc3 (Изменение README.md.)
=======


## Примеры SQL-запросов

### Получить все фильмы
```sql
SELECT * 
FROM films;
```

### Получить всех пользователей
```sql
SELECT * 
FROM users;
```

### Найти топ-5 самых популярных фильмов (по количеству лайков)
```sql
SELECT f.id, f.name, COUNT(l.user_id) AS likes_count
FROM films f
LEFT JOIN likes l ON f.id = l.film_id
GROUP BY f.id, f.name
ORDER BY likes_count DESC
LIMIT 5;
```

### Получить список друзей пользователя
```sql
SELECT u.id, u.name, fs.status
FROM friendships fs
JOIN users u ON fs.friend_id = u.id
WHERE fs.user_id = 1;
```

### Получить общих друзей двух пользователей
```sql
SELECT u.id, u.name
FROM friendships fs1
JOIN friendships fs2 ON fs1.friend_id = fs2.friend_id
JOIN users u ON fs1.friend_id = u.id
WHERE fs1.user_id = 1 
  AND fs2.user_id = 2
  AND fs1.status = 'CONFIRMED'
  AND fs2.status = 'CONFIRMED';
```
>>>>>>> f53bbc2 (Изменение README.md.)
=======
```
>>>>>>> a98b57d (Migrate clean changes from add-friends-likes excluding ignored/binary files)
=======
```
>>>>>>> dfb8514 (Исправление ошибок.)
=======
```
>>>>>>> 173cf76 (Update .gitignore)
