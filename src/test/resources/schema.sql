-- Таблица пользователей
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255),
    login VARCHAR(50),
    name VARCHAR(100),
    birthday DATE
);

-- Таблица рейтингов MPA
CREATE TABLE mpa_rating (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50)
);

-- Таблица фильмов
CREATE TABLE films (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    description VARCHAR(500),
    release_date DATE,
    duration INT,
    mpa_rating_id INT,
    CONSTRAINT fk_mpa_rating FOREIGN KEY (mpa_rating_id) REFERENCES mpa_rating(id)
);

-- Таблица жанров
CREATE TABLE genres (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50)
);

-- Таблица связи фильмов и жанров (многие ко многим)
CREATE TABLE film_genres (
    film_id INT,
    genre_id INT,
    PRIMARY KEY (film_id, genre_id),
    CONSTRAINT fk_film FOREIGN KEY (film_id) REFERENCES films(id),
    CONSTRAINT fk_genre FOREIGN KEY (genre_id) REFERENCES genres(id)
);

-- Таблица лайков фильмов пользователями
CREATE TABLE likes (
    film_id INT,
    user_id INT,
    PRIMARY KEY (film_id, user_id),
    CONSTRAINT fk_like_film FOREIGN KEY (film_id) REFERENCES films(id),
    CONSTRAINT fk_like_user FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Таблица дружбы между пользователями
CREATE TABLE friendships (
    user_id INT,
    friend_id INT,
    status VARCHAR(10),
    PRIMARY KEY (user_id, friend_id),
    CONSTRAINT fk_user_friend FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_friend FOREIGN KEY (friend_id) REFERENCES users(id)
);
