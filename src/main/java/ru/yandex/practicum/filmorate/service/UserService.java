package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.NotFound;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserService {
    private final UserStorage userStorage;

    public UserService(InMemoryUserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public List<User> allUsers() {
        return userStorage.getAllUser();
    }

    public User addUsers(User user) {
        return userStorage.createUser(user);
    }

    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }

    public void deleteAllUser() {
        userStorage.deleteAllUser();
    }

    public User getUserById(long id) throws NotFound {
        return userStorage.getUserById(id);
    }

    public User addFriend(long userId, long friendId) throws NotFound {
        final User user = userStorage.getUserById(userId);
        final User friend = userStorage.getUserById(friendId);
        if (friend == null || friend.getId() < 0) {
            throw new NotFound("отрицательное число id: " + friendId);
        } else if (user == null || user.getId() < 0) {
            throw new NotFound("нет пользователя с id " + userId);
        } else {
            if (friend.getFriends().containsKey(userId)) {
                friend.getFriends().put(userId, true);
                user.getFriends().put(friendId, true);
            } else {
                user.getFriends().put(friendId, false);
            }
            userStorage.updateUser(friend);
            return userStorage.updateUser(user);
        }
    }

    public User deleteFriend(long userId, long friendId) throws NotFound {
        User user = userStorage.getUserById(userId);
        User friend = userStorage.getUserById(friendId);
        if (user == null || user.getId() < 0) {
            throw new NotFound("нет пользователя с id " + userId);
        } else if (friend == null || friend.getId() < 0 || !user.isConfirmed()) {
            throw new NotFound("нет пользователя с id " + friendId);
        } else {
            user.getFriends().remove(friendId);
            friend.getFriends().remove(userId);
            userStorage.updateUser(friend);
            return userStorage.updateUser(user);
        }
    }

    public List<User> getFriendsList(long userId) throws NotFound {
        User user = userStorage.getUserById(userId);
        List<User> friendsList = new ArrayList<>();
        if (user == null || user.getId() < 0) {
            throw new NotFound("Пользователя с id" + userId + " не существует");
        } else {
            if (user.getFriends() != null) {
                for (Long id : user.getFriends().keySet()) {
                    if (user.getFriends().get(id)) {
                        friendsList.add(userStorage.getUserById(id));
                    }
                }
            }
        }
        return friendsList;
    }

    public List<User> getCommonFriendsList(long mainUser, long otherUserId) throws NotFound {
        List<User> commonFriends = new ArrayList<>();
        User user = userStorage.getUserById(mainUser);
        User otherUser = userStorage.getUserById(otherUserId);

        if (user == null) {
            throw new NotFound("нет пользователя с id " + mainUser);
        } else if (otherUser == null) {
            throw new NotFound("нет пользователя с id " + otherUserId);
        } else {
            if (user.getFriends() == null) {
                return List.of();
            } else if (otherUser.getFriends() == null) {
                return List.of();
            } else {
                for (long id : user.getFriends().keySet()) {
                    if (otherUser.getFriends().containsKey(id)) {
                        commonFriends.add(userStorage.getUserById(id));
                    }
                }
            }
        }
        return commonFriends;
    }
}

