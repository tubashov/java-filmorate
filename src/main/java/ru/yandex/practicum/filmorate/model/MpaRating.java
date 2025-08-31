package ru.yandex.practicum.filmorate.model;

public enum MpaRating {
    G(1, "G"),
    PG(2, "PG"),
    PG13(3, "PG-13"),
    R(4, "R"),
    NC17(5, "NC-17"),
    NR(6, "NR");

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
        return NR;
    }
}

