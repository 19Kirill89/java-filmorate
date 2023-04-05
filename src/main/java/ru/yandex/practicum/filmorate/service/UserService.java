package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;

    public User create(User user) {
        return userStorage.create(user);
    }

    public User update(User user) {
        return userStorage.update(user);
    }

    public User delete(int userId) {
        return userStorage.delete(userId);
    }

    public User getUser(int userId) {
        return userStorage.getUser(userId);
    }

    public Collection<User> getUsers() {
        return userStorage.getUsers();
    }

    public User addFriend(int userId, int friendId) {
        return userStorage.addFriend(userId, friendId);
    }

    public User deleteFriend(int userId, int friendId) {
        return userStorage.deleteFriend(userId, friendId);
    }

    public List<User> getFriends(int userId) {
        return userStorage.getFriends(userId);
    }

    public List<User> getCommonFriends(int userId, int otherUserId) {
        return userStorage.getCommonFriends(userId, otherUserId);
    }
}
