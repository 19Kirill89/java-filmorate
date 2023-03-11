package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.ControllersException;
import ru.yandex.practicum.filmorate.exeption.UserNotFoundException;
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

    @Autowired
    public UserService(InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    public List<User> allUsers() {
        return inMemoryUserStorage.getAllUser();
    }

    public User addUsers(User user) {
        log.info("POST запрос на добавление: новый пользователь создан");
        return inMemoryUserStorage.createUser(user);
    }

    public User updateUser(User user) throws ControllersException {
        log.info("POST запрос на обновление пользователя - пользователь обновлен");
        return inMemoryUserStorage.updateUser(user);
    }

    public void deleteAllUser() throws ControllersException {
        log.info("удалены все пользователи");
        inMemoryUserStorage.deleteAllUser();
    }

    public User getUserById(long id) throws ControllersException {
        return inMemoryUserStorage.getUserById(id);
    }

    /*public User addFriends(long userId, long friendId) throws UserNotFoundException {
         User user = inMemoryUserStorage.getUserById(userId);
         User friend = inMemoryUserStorage.getUserById(friendId);
        if (user == null) {
            throw new UserNotFoundException("пользователь с id: " + userId + " не существует.");
        } else if (friend == null) {
            throw new UserNotFoundException("пользователь с id: " + friendId + " не существует.");
        } else {
            inMemoryUserStorage.getUserById(userId).getFriends().add(friendId);
        }
        return user;
    }*/


    public User addFriends(Long userId, Long friendId) throws UserNotFoundException {
        final User user = inMemoryUserStorage.getUserById(userId);
        final User friend = inMemoryUserStorage.getUserById(friendId);
        if (user == null) {
            throw new UserNotFoundException("нет пользователя с id " + userId);
        } else if (friend == null) {
            throw new UserNotFoundException("нет пользователя с id " + friendId);
        } else {
            Set<Long> idUserFriends = new HashSet<>();
            if (user.getFriends() != null) {
                idUserFriends = user.getFriends();
            }
            idUserFriends.add(friendId);
            user.setFriends(idUserFriends);

            Set<Long> idFriendsOfUserFriends = new HashSet<>();
            if (friend.getFriends() != null) {
                idFriendsOfUserFriends = friend.getFriends();
            }
            idFriendsOfUserFriends.add(userId);
            friend.setFriends(idFriendsOfUserFriends);
            inMemoryUserStorage.updateUser(friend);
            return inMemoryUserStorage.updateUser(user);
        }
    }

    public User deleteFriends(Long userId, Long friendId) throws UserNotFoundException {
        User user = inMemoryUserStorage.getUserById(userId);
        User friend = inMemoryUserStorage.getUserById(friendId);
        if (user == null) {
            throw new UserNotFoundException("нет пользователя с id " + userId);
        } else if (friend == null) {
            throw new UserNotFoundException("нет пользователя с id " + friendId);
        } else {
            Set<Long> idUser = new HashSet<>();
            if (user.getFriends() != null) {
                idUser = user.getFriends();
            }
            idUser.remove(friendId);
            user.setFriends(idUser);

            Set<Long> idFriend = new HashSet<>();
            if (friend.getFriends() != null) {
                idFriend = friend.getFriends();
            }
            idFriend.remove(userId);
            friend.setFriends(idFriend);
            inMemoryUserStorage.updateUser(friend);
            return inMemoryUserStorage.updateUser(user);
        }
    }

    public List<User> getFriendsList(Long userId) throws UserNotFoundException {
        User user = inMemoryUserStorage.getUserById(userId);
        if (user == null) {
            throw new UserNotFoundException("Пользователя с id" + userId + " не существует");
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

    public List<User> getCommonFriendsList(Long mainUser, Long otherUserId) throws UserNotFoundException {
        List<User> commonFriends = new ArrayList<>();
        User user = inMemoryUserStorage.getUserById(mainUser);
        User otherUser = inMemoryUserStorage.getUserById(otherUserId);

        if (user == null) {
            throw new UserNotFoundException("нет пользователя с id " + mainUser);
        } else if (otherUser == null) {
            throw new UserNotFoundException("нет пользователя с id " + otherUserId);
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

