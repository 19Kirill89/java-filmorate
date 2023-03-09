package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeption.ControllersExeption;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
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

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String negative(final ControllersExeption e) {
        return "Ошибка: " + e.getMessage();
    }
}