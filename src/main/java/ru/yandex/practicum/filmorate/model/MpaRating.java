package ru.yandex.practicum.filmorate.model;

import ru.yandex.practicum.filmorate.exception.NotFoundException;

public enum MpaRating {
<<<<<<< HEAD
    G,       // нет возрастных ограничений
    PG,      // детям рекомендуется с родителями
    PG_13,   // детям до 13 нежелательно
    R,       // до 17 — только с родителями
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
    NC_17,    // до 18 запрещено
    NR       // Not Rated (дефолтное значение)
=======
    NC_17    // до 18 запрещено
>>>>>>> 9bcac66 (Добавление жанра и возрастного ограничения.)
=======
    NC_17,    // до 18 запрещено
    NR       // Not Rated (дефолтное значение)
>>>>>>> 7b18731 (Добавление жанра жильма, статуса заявки в друзья, возрастного ограничения.)
=======
    NC_17,    // до 18 запрещено
    NR       // Not Rated (дефолтное значение)
>>>>>>> a98b57d (Migrate clean changes from add-friends-likes excluding ignored/binary files)
=======
    G(1, "G"),
    PG(2, "PG"),
    PG13(3, "PG-13"),
    R(4, "R"),
    NC17(5, "NC-17");

    private final int id;
    private final String name;

    MpaRating(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }
    public String getName() { return name; }

    public static MpaRating fromId(int id) {
        for (MpaRating rating : values()) {
            if (rating.id == id) return rating;
        }
        throw new NotFoundException("MPA Rating с ID " + id + " не найден");
    }
>>>>>>> b988486 (Исправление ошибок)
}
