package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
<<<<<<< HEAD
<<<<<<< HEAD
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
        return new ResponseEntity<>(userService.addUser(user), HttpStatus.CREATED);
=======
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
>>>>>>> c71e278 (Исправление ошибок в методах getAllUsers, removeFriend.)
=======
        return new ResponseEntity<>(userService.addUser(user), HttpStatus.CREATED);
>>>>>>> 4c4e1a9 (Исправление метода removeFriend.)
=======
        return new ResponseEntity<>(userService.addUser(user), HttpStatus.CREATED);
>>>>>>> a98b57d (Migrate clean changes from add-friends-likes excluding ignored/binary files)
=======
        User savedUser = userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
>>>>>>> c942a69 (Исправление ошибок. Инициализация friends-безопасный. Проверка подтверждения дружбы.)
=======
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUser(user));
>>>>>>> 284ec40 (Исправление ошибок.)
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
<<<<<<< HEAD
    public ResponseEntity<User> getUserById(@PathVariable int id) {
<<<<<<< HEAD
        return ResponseEntity.ok(userService.getUserById(id));
<<<<<<< HEAD
<<<<<<< HEAD
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(user));
    }

    // Добавить друга
    @PutMapping("/{userId}/friends/{friendId}")
    public ResponseEntity<Void> addFriend(@PathVariable int userId, @PathVariable int friendId) {
        userService.addFriend(userId, friendId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Удалить друга
    @DeleteMapping("/{userId}/friends/{friendId}")
    public ResponseEntity<Void> removeFriend(@PathVariable int userId, @PathVariable int friendId) {
        userService.removeFriend(userId, friendId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}/friends")
    public ResponseEntity<List<User>> getFriends(@PathVariable int id) {
        return ResponseEntity.ok(userService.getFriends(id));
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public ResponseEntity<List<User>> getCommonFriends(@PathVariable int id, @PathVariable int otherId) {
        return ResponseEntity.ok(userService.getCommonFriends(id, otherId));
=======
>>>>>>> c71e278 (Исправление ошибок в методах getAllUsers, removeFriend.)
=======
>>>>>>> a98b57d (Migrate clean changes from add-friends-likes excluding ignored/binary files)
=======
        return ResponseEntity.ok(userService.findUserById(id));
>>>>>>> b988486 (Исправление ошибок)
=======
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
<<<<<<< HEAD
        User user = userService.findUserById(id);
        return ResponseEntity.ok(user);
>>>>>>> c942a69 (Исправление ошибок. Инициализация friends-безопасный. Проверка подтверждения дружбы.)
=======
        return ResponseEntity.ok(userService.findUserById(id));
>>>>>>> 284ec40 (Исправление ошибок.)
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user) {
        log.info("Update user: {}", user);
        return ResponseEntity.ok(userService.updateUser(user));
    }

    @PutMapping("/{userId}/friends/{friendId}")
    public ResponseEntity<Void> addFriend(@PathVariable Integer userId, @PathVariable Integer friendId) {
        userService.addFriend(userId, friendId);
        return ResponseEntity.ok().build(); // 200 OK
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public ResponseEntity<Void> removeFriend(@PathVariable Integer userId, @PathVariable Integer friendId) {
        userService.removeFriend(userId, friendId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/friends")
    public ResponseEntity<List<User>> getFriends(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getFriends(id));
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public ResponseEntity<List<User>> getCommonFriends(
            @PathVariable Integer id,
            @PathVariable Integer otherId
    ) {
        return ResponseEntity.ok(userService.getCommonFriends(id, otherId));
    }
}
