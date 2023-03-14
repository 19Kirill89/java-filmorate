package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeption.NotFound;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {

    private final HashMap<Long, User> userHashMap = new HashMap<>();
    private long idCounter;

    @Override
    public User createUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.debug("установлен логин в поле имя т.к. имя не было задано");
            log.info("POST запрос на добавление: новый пользователь создан");
        }
        user.setId(++idCounter);
        userHashMap.put(user.getId(), user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        if (!userHashMap.containsKey(user.getId())) {
            throw new NotFound("нет пользователя с ID " + user.getId());
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.debug("установлен логин, имя не было задано");
            log.info("POST запрос на обновление пользователя - пользователь обновлен");
        }
        userHashMap.put(user.getId(), user);
        return user;
    }

    @Override
    public List<User> getAllUser() {
        log.info("удалены все пользователи");
        return new ArrayList<>(userHashMap.values());
    }

    @Override
    public User getUserById(long id) {
        if (!userHashMap.containsKey(id)) {
            log.debug("запрос по id пользовател: " + id);
            throw new NotFound("нет пользователя id: " + id);
        }
        return userHashMap.get(id);
    }

    @Override
    public void deleteAllUser() {
        userHashMap.clear();
    }
}