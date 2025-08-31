-- USERS
CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL UNIQUE,
    login VARCHAR(255) NOT NULL,
    name VARCHAR(255),
    birthday DATE NOT NULL
);

-- MPA
CREATE TABLE IF NOT EXISTS mpa_rating (
    id INT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

-- FILMS
CREATE TABLE IF NOT EXISTS films (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(200) NOT NULL,
    release_date DATE NOT NULL,
    duration INT NOT NULL CHECK (duration > 0),
    mpa_id INT NOT NULL,
    CONSTRAINT fk_films_mpa FOREIGN KEY (mpa_id) REFERENCES mpa_rating(id)
);

-- GENRES
CREATE TABLE IF NOT EXISTS genres (
    id INT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

-- FILM ⇄ GENRE (many-to-many)
CREATE TABLE IF NOT EXISTS film_genres (
    film_id INT NOT NULL,
    genre_id INT NOT NULL,
    PRIMARY KEY (film_id, genre_id),
    CONSTRAINT fk_fg_film  FOREIGN KEY (film_id)  REFERENCES films(id)  ON DELETE CASCADE,
    CONSTRAINT fk_fg_genre FOREIGN KEY (genre_id) REFERENCES genres(id) ON DELETE CASCADE
);

-- LIKES (кто лайкнул фильм)
CREATE TABLE IF NOT EXISTS likes (
    film_id INT NOT NULL,
    user_id INT NOT NULL,
    PRIMARY KEY (film_id, user_id),
    CONSTRAINT fk_likes_film FOREIGN KEY (film_id) REFERENCES films(id) ON DELETE CASCADE,
    CONSTRAINT fk_likes_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- FRIENDSHIPS (дружба со статусом)
CREATE TABLE IF NOT EXISTS friendships (
    user_id   INT NOT NULL,
    friend_id INT NOT NULL,
    status    VARCHAR(20) NOT NULL, -- PENDING | CONFIRMED
    PRIMARY KEY (user_id, friend_id),
    CONSTRAINT fk_fr_user   FOREIGN KEY (user_id)   REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_fr_friend FOREIGN KEY (friend_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT chk_no_self_friend CHECK (user_id <> friend_id)
);

-- Индексы (ускоряют популярные выборки)
CREATE INDEX IF NOT EXISTS idx_likes_film        ON likes(film_id);
CREATE INDEX IF NOT EXISTS idx_likes_user        ON likes(user_id);
CREATE INDEX IF NOT EXISTS idx_fg_film           ON film_genres(film_id);
CREATE INDEX IF NOT EXISTS idx_fg_genre          ON film_genres(genre_id);
CREATE INDEX IF NOT EXISTS idx_friendships_user  ON friendships(user_id);
