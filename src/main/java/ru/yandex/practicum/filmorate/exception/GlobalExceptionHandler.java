package ru.yandex.practicum.filmorate.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
@RestControllerAdvice // Глобальный обработчик исключений для всех контроллеров
public class GlobalExceptionHandler {

<<<<<<< HEAD
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class) // Ошибки валидации @Valid (тело запроса)
=======
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class); // Логгер SLF4J

    @ExceptionHandler(MethodArgumentNotValidException.class) // Ошибки валидации @Valid
>>>>>>> 39cd978 (Добавление логирония в класс GlobalExceptionHandler.)
=======
=======
=======
>>>>>>> 173cf76 (Update .gitignore)
/**
 * Глобальный обработчик исключений.
 */
@Slf4j
<<<<<<< HEAD
>>>>>>> 284ec40 (Исправление ошибок.)
=======
>>>>>>> 173cf76 (Update .gitignore)
@RestControllerAdvice
public class GlobalExceptionHandler {


<<<<<<< HEAD
<<<<<<< HEAD
    @ExceptionHandler(MethodArgumentNotValidException.class) // Ошибки валидации @Valid (тело запроса)
>>>>>>> a98b57d (Migrate clean changes from add-friends-likes excluding ignored/binary files)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> a98b57d (Migrate clean changes from add-friends-likes excluding ignored/binary files)
        log.warn("Ошибка валидации тела запроса: {}", errors);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
=======
=======
>>>>>>> 173cf76 (Update .gitignore)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        log.warn("Validation error: {}", errors);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errors);
<<<<<<< HEAD
>>>>>>> 284ec40 (Исправление ошибок.)
=======
>>>>>>> 173cf76 (Update .gitignore)
    }

    @ExceptionHandler(ConstraintViolationException.class) // Ошибки валидации параметров
    public ResponseEntity<Map<String, String>> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation ->
                errors.put(violation.getPropertyPath().toString(), violation.getMessage()));

        //log.warn("Ошибка валидации параметров: {}", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

<<<<<<< HEAD
<<<<<<< HEAD
    @ExceptionHandler(ValidationException.class) // Кастомная ошибка валидации
<<<<<<< HEAD
=======
        log.warn("Ошибка валидации: {}", errors); // Логируем поля с ошибками
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class) // Кастомное исключение валидации
>>>>>>> 39cd978 (Добавление логирония в класс GlobalExceptionHandler.)
=======
>>>>>>> a98b57d (Migrate clean changes from add-friends-likes excluding ignored/binary files)
=======
    @ExceptionHandler(ValidationException.class) // Кастомная валидация
>>>>>>> 284ec40 (Исправление ошибок.)
=======
    @ExceptionHandler(ValidationException.class) // Кастомная валидация
>>>>>>> 173cf76 (Update .gitignore)
    public ResponseEntity<Map<String, String>> handleValidation(ValidationException ex) {
        //log.warn("ValidationException: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", ex.getMessage()));
    }

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
    @ExceptionHandler(NotFoundException.class) // Универсальная ошибка "не найдено"
=======
    @ExceptionHandler(NotFoundException.class) // Ошибка "не найдено"
>>>>>>> 284ec40 (Исправление ошибок.)
    public ResponseEntity<Map<String, String>> handleNotFound(NotFoundException ex) {
        log.warn("NotFoundException: {}", ex.getMessage());
=======
    @ExceptionHandler(FilmNotFoundException.class) // Фильм не найден
    public ResponseEntity<Map<String, String>> handleFilmNotFound(FilmNotFoundException ex) {
        log.warn("FilmNotFoundException: {}", ex.getMessage());
>>>>>>> 39cd978 (Добавление логирония в класс GlobalExceptionHandler.)
=======
    @ExceptionHandler(NotFoundException.class) // Универсальная ошибка "не найдено"
    public ResponseEntity<Map<String, String>> handleNotFound(NotFoundException ex) {
        log.warn("NotFoundException: {}", ex.getMessage());
>>>>>>> a98b57d (Migrate clean changes from add-friends-likes excluding ignored/binary files)
=======
    @ExceptionHandler(NotFoundException.class) // Ошибка "не найдено"
    public ResponseEntity<Map<String, String>> handleNotFound(NotFoundException ex) {
        log.warn("NotFoundException: {}", ex.getMessage());
>>>>>>> 173cf76 (Update .gitignore)
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", ex.getMessage()));
    }

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
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
=======
    @ExceptionHandler(Exception.class) // Любая непредвиденная ошибка
    public ResponseEntity<Map<String, String>> handleGeneralError(Exception ex) {
        log.error("Необработанная ошибка: ", ex);
>>>>>>> a98b57d (Migrate clean changes from add-friends-likes excluding ignored/binary files)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
=======
=======
>>>>>>> 173cf76 (Update .gitignore)
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFound(UserNotFoundException ex) {
        //log.warn("UserNotFoundException: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class) // Прочие ошибки
    public ResponseEntity<Map<String, String>> handleGeneralError(Exception ex) {
        log.error("Необработанная ошибка: ", ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
<<<<<<< HEAD
>>>>>>> 284ec40 (Исправление ошибок.)
=======
>>>>>>> 173cf76 (Update .gitignore)
                .body(Map.of("error", ex.getMessage()));
    }
}
