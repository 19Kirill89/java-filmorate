package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.NotFound;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class UserService {
    private final InMemoryUserStorage inMemoryUserStorage;


    public UserService(InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    public List<User> allUsers() {
        return inMemoryUserStorage.getAllUser();
    }

    public User addUsers(User user) {
        return inMemoryUserStorage.createUser(user);
    }

    public User updateUser(User user) {
        return inMemoryUserStorage.updateUser(user);
    }

    public void deleteAllUser() {
        inMemoryUserStorage.deleteAllUser();
    }

    public User getUserById(long id) throws NotFound {
        return inMemoryUserStorage.getUserById(id);
    }

    public User addFriends(Long userId, Long friendId) throws NotFound {
        final User user = inMemoryUserStorage.getUserById(userId);
        final User friend = inMemoryUserStorage.getUserById(friendId);
        if (friend.getId() < 0 ) {
            throw new NotFound("отрицательное число id: " + friendId);
        } else if (user == null) {
            throw new NotFound("нет пользователя с id " + userId);
        } else {
            user.getFriends().add(friendId);
            friend.getFriends().add(userId);
            inMemoryUserStorage.updateUser(friend);
            return inMemoryUserStorage.updateUser(user);
        }
    }

    public User deleteFriends(Long userId, Long friendId) throws NotFound {
        User user = inMemoryUserStorage.getUserById(userId);
        User friend = inMemoryUserStorage.getUserById(friendId);
        if (user == null) {
            throw new NotFound("нет пользователя с id " + userId);
        } else if (friend == null) {
            throw new NotFound("нет пользователя с id " + friendId);
        } else {
            user.getFriends().remove(friendId);
            friend.getFriends().remove(userId);
            inMemoryUserStorage.updateUser(friend);
            return inMemoryUserStorage.updateUser(user);
        }
    }

    public List<User> getFriendsList(Long userId) throws NotFound {
        User user = inMemoryUserStorage.getUserById(userId);
        if (user == null) {
            throw new NotFound("Пользователя с id" + userId + " не существует");
        } else {
            List<User> friendsList = new ArrayList<>();
            if (user.getFriends() != null) {
                for (Long id : user.getFriends()) {
                    friendsList.add(inMemoryUserStorage.getUserById(id));
                }
            }
            return friendsList;
        }
    }

    public List<User> getCommonFriendsList(Long mainUser, Long otherUserId) throws NotFound {
        List<User> commonFriends = new ArrayList<>();
        User user = inMemoryUserStorage.getUserById(mainUser);
        User otherUser = inMemoryUserStorage.getUserById(otherUserId);

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
                        commonFriends.add(inMemoryUserStorage.getUserById(id));
                    }
                    return commonFriends;
                }
            } else {
                Set<Long> duplicateFriendsUser = new HashSet<>(user.getFriends());
                duplicateFriendsUser.retainAll(otherUser.getFriends());

                for (Long id : duplicateFriendsUser) {
                    commonFriends.add(inMemoryUserStorage.getUserById(id));
                }
                return commonFriends;
            }
        }
    }
}

