package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeption.ControllersException;
import ru.yandex.practicum.filmorate.exeption.ErrorResponse;
import ru.yandex.practicum.filmorate.exeption.NotFound;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import javax.validation.ValidationException;
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

    @GetMapping
    public List<User> getAllUsers() {
        return userService.allUsers();
    }

    @PostMapping
    public User addUsers(@Valid @RequestBody User user) {
        return userService.addUsers(user);
    }

    @PutMapping
    public User updateInfoOfUser(@Valid @RequestBody User user) {
        return userService.updateUser(user);
    }

    @GetMapping("/{userId}")
    public User getById(@PathVariable long userId) throws NotFound {
        return userService.getUserById(userId);
    }

    @PutMapping("/{userId}/friends/{friendId}")
    public User addToFriends(@Valid @PathVariable long userId, @Valid @PathVariable long friendId)
            throws NotFound {
        log.debug("PUT запрос на добавление друга к пользователю id ={} друга id={}", userId, friendId);
        return userService.addFriends(userId, friendId);
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public User deleteFromFriends(@PathVariable long userId, @PathVariable long friendId) throws NotFound {
        log.debug("DELETE запрос на удаление из друзей пользователя с id = {} у пользователя c id = {}",
                friendId, userId);
        return userService.deleteFriends(userId, friendId);
    }

    @GetMapping("/{userId}/friends")
    public List<User> getFriendsForUser(@PathVariable long userId) throws NotFound {
        log.debug("GET запрос на получение списка дружков");
        return userService.getFriendsList(userId);
    }

    @GetMapping("/{userId}/friends/common/{otherUserId}")
    public List<User> getCommonFriends(@PathVariable long userId, @PathVariable long otherUserId)
            throws NotFound {
        log.debug("GET запрос на получение списка дружков которые друзья пользователя");
        return userService.getCommonFriendsList(userId, otherUserId);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserNotFound(final NotFound e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleServerError(final RuntimeException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String negative(final ValidationException e) {
        return e.getMessage();
    }

}