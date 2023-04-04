package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.info("POST запросна добавление нового пользователя.");
        return userService.create(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        log.info("POST запрос на обновление пользователя.");
        return userService.update(user);
    }

    @DeleteMapping("/{id}")
    public User delete(@PathVariable("id") int userId) {
        return userService.delete(userId);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") int userId) {
        return userService.getUser(userId);
    }

    @GetMapping
    public Collection<User> getUsers() {
        log.info("GET запрос на получение всех пользователей.");
        return userService.getUsers();
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addFriend(@PathVariable("id") int userId, @PathVariable("friendId") int friendId) {
        return userService.addFriend(userId, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User deleteFriend(@PathVariable("id") int userId, @PathVariable("friendId") int friendId) {
        return userService.deleteFriend(userId, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable("id") int userId) {
        return userService.getFriends(userId);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable("id") int userId,
                                       @PathVariable("otherId") int otherUserId) {
        return userService.getCommonFriends(userId, otherUserId);
    }
}
