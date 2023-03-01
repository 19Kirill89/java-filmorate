package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeption.ControllersExeption;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final HashMap<Long, User> userHashMap = new HashMap<>();
    private long idCounter;

    @GetMapping
    public List<User> allUsers() {
        return new ArrayList<>(userHashMap.values());
    }

    @PostMapping
    public User addUsers(@Valid @RequestBody User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.debug("установлен логин, имя не было задано");
        }
        user.setId(++idCounter);
        userHashMap.put(user.getId(), user);
        log.debug("POST запрос на добавление: новый пользователь создан");
        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) throws ControllersExeption {
        if (!userHashMap.containsKey(user.getId())) {
            throw new ControllersExeption("нет пользователя с ID " + user.getId());
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.debug("установлен логин, имя не было задано");
        }
        userHashMap.put(user.getId(), user);
        log.debug("POST запрос на обновление пользователя - пользователь обновлен");
        return user;
    }
}