package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.NotFound;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public User addFriends(Long userId, Long friendId) throws NotFound {
        final User user = userStorage.getUserById(userId);
        final User friend = userStorage.getUserById(friendId);
        if (friend == null || friend.getId() < 0) {
            throw new NotFound("отрицательное число id: " + friendId);
        } else if (user == null || user.getId() < 0) {
            throw new NotFound("нет пользователя с id " + userId);
        } else {
            user.getFriends().add(friendId);
            friend.getFriends().add(userId);
            userStorage.updateUser(friend);
            return userStorage.updateUser(user);
        }
    }

    public User deleteFriends(Long userId, Long friendId) throws NotFound {
        User user = userStorage.getUserById(userId);
        User friend = userStorage.getUserById(friendId);
        if (user == null || user.getId() < 0) {
            throw new NotFound("нет пользователя с id " + userId);
        } else if (friend == null || friend.getId() < 0) {
            throw new NotFound("нет пользователя с id " + friendId);
        } else {
            user.getFriends().remove(friendId);
            friend.getFriends().remove(userId);
            userStorage.updateUser(friend);
            return userStorage.updateUser(user);
        }
    }

    public List<User> getFriendsList(Long userId) throws NotFound {
        User user = userStorage.getUserById(userId);
        if (user == null || user.getId() < 0) {
            throw new NotFound("Пользователя с id" + userId + " не существует");
        } else {
            List<User> friendsList = new ArrayList<>();
            if (user.getFriends() != null) {
                for (Long id : user.getFriends()) {
                    friendsList.add(userStorage.getUserById(id));
                }
            }
            return friendsList;
        }
    }

    public List<User> getCommonFriendsList(Long mainUser, Long otherUserId) throws NotFound {
        List<User> commonFriends = new ArrayList<>();
        User user = userStorage.getUserById(mainUser);
        User otherUser = userStorage.getUserById(otherUserId);

        if (user == null) {
            throw new NotFound("нет пользователя с id " + mainUser);
        } else if (otherUser == null) {
            throw new NotFound("нет пользователя с id " + otherUserId);
        } else {
            if (user.getFriends() == null) {
                if (otherUser.getFriends() == null) {
                    return List.of();
                } else {
                    for (Long id : otherUser.getFriends()) {
                        commonFriends.add(userStorage.getUserById(id));
                    }
                    return commonFriends;
                }
            } else {
                Set<Long> duplicateFriendsUser = new HashSet<>(user.getFriends());
                duplicateFriendsUser.retainAll(otherUser.getFriends());

                for (Long id : duplicateFriendsUser) {
                    commonFriends.add(userStorage.getUserById(id));
                }
                return commonFriends;
            }
        }
    }
}