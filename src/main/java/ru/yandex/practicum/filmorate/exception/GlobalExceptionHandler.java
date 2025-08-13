package ru.yandex.practicum.filmorate.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice // Глобальный обработчик исключений для всех контроллеров
public class GlobalExceptionHandler {

<<<<<<< HEAD
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class) // Ошибки валидации @Valid (тело запроса)
=======
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class); // Логгер SLF4J

    @ExceptionHandler(MethodArgumentNotValidException.class) // Ошибки валидации @Valid
>>>>>>> 39cd978 (Добавление логирония в класс GlobalExceptionHandler.)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

<<<<<<< HEAD
        log.warn("Ошибка валидации тела запроса: {}", errors);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class) // Ошибки валидации параметров метода/контроллера
    public ResponseEntity<Map<String, String>> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String fieldName = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            errors.put(fieldName, message);
        });

        log.warn("Ошибка валидации параметров: {}", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(ValidationException.class) // Кастомная ошибка валидации
=======
        log.warn("Ошибка валидации: {}", errors); // Логируем поля с ошибками
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class) // Кастомное исключение валидации
>>>>>>> 39cd978 (Добавление логирония в класс GlobalExceptionHandler.)
    public ResponseEntity<Map<String, String>> handleValidation(ValidationException ex) {
        log.warn("ValidationException: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", ex.getMessage()));
    }

<<<<<<< HEAD
    @ExceptionHandler(NotFoundException.class) // Универсальная ошибка "не найдено"
    public ResponseEntity<Map<String, String>> handleNotFound(NotFoundException ex) {
        log.warn("NotFoundException: {}", ex.getMessage());
=======
    @ExceptionHandler(FilmNotFoundException.class) // Фильм не найден
    public ResponseEntity<Map<String, String>> handleFilmNotFound(FilmNotFoundException ex) {
        log.warn("FilmNotFoundException: {}", ex.getMessage());
>>>>>>> 39cd978 (Добавление логирония в класс GlobalExceptionHandler.)
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", ex.getMessage()));
    }

<<<<<<< HEAD
    @ExceptionHandler(Exception.class) // Любая непредвиденная ошибка
    public ResponseEntity<Map<String, String>> handleGeneralError(Exception ex) {
        log.error("Необработанная ошибка: ", ex);
=======
    @ExceptionHandler(UserNotFoundException.class) // Пользователь не найден
    public ResponseEntity<Map<String, String>> handleUserNotFound(UserNotFoundException ex) {
        log.warn("UserNotFoundException: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class) // Непредвиденная ошибка
    public ResponseEntity<Map<String, String>> handleGeneralError(Exception ex) {
        log.error("Необработанная ошибка: ", ex); // Логируем полный стектрейс
>>>>>>> 39cd978 (Добавление логирония в класс GlobalExceptionHandler.)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", ex.getMessage()));
    }
}
