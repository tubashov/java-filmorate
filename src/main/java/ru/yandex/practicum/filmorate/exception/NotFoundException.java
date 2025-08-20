package ru.yandex.practicum.filmorate.exception;

/**
 * Универсальное исключение для ситуации, когда сущность не найдена.
 * Подходит для любых типов ресурсов (пользователь, фильм, заказ и т.д.).
 */
public class NotFoundException extends RuntimeException {

    private final String entityName; // Название сущности (например, "Пользователь")
    private final Object identifier; // Идентификатор сущности (ID, UUID, email и т.д.)

    public NotFoundException(String entityName, Object identifier) {
        super(String.format("%s с идентификатором %s не найден", entityName, identifier));
        this.entityName = entityName;
        this.identifier = identifier;
    }

    public NotFoundException(String message) {
        super(message);
        this.entityName = null;
        this.identifier = null;
    }

    public String getEntityName() {
        return entityName;
    }

    public Object getIdentifier() {
        return identifier;
    }
}
