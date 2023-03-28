package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class ServiceUserTest {
    private UserService userService;

    @BeforeEach
    public void userServiceInit() {
        userService = new UserService(new InMemoryUserStorage());
    }

    @Test
    public void addFriendsTest() {
        User user = new User();
        user.setEmail("ffff@gmail.com");
        user.setLogin("M-9");
        user.setBirthday(LocalDate.of(1956, 10, 12));

        User userFriend = new User();
        userFriend.setName("Пчела");
        userFriend.setEmail("afafaf1214@gmail.com");
        userFriend.setLogin("ПЧ-9");
        userFriend.setBirthday(LocalDate.of(1956, 10, 12));

        User userFriend2 = new User();
        userFriend.setName("КТ-9");
        userFriend.setEmail("afafaf1214@gmail.com");
        userFriend.setLogin("Катюша");
        userFriend.setBirthday(LocalDate.of(1956, 10, 12));

        userService.addUsers(user);
        userService.addUsers(userFriend);
        userService.addUsers(userFriend2);
        userService.addFriend(user.getId(), userFriend.getId());
        userService.addFriend(user.getId(), userFriend2.getId());
        long expected = 2;
        Assertions.assertEquals(expected, user.getFriends().size());
    }

    @Test
    public void deleteUser() {
        User user = new User();
        user.setEmail("ffff@gmail.com");
        user.setLogin("M-9");
        user.setBirthday(LocalDate.of(1956, 10, 12));

        User userFriend = new User();
        userFriend.setName("Пчела");
        userFriend.setEmail("afafaf1214@gmail.com");
        userFriend.setLogin("ПЧ-9");
        userFriend.setBirthday(LocalDate.of(1956, 10, 12));

        User userFriend2 = new User();
        userFriend.setName("КТ-9");
        userFriend.setEmail("afafaf1214@gmail.com");
        userFriend.setLogin("Катюша");
        userFriend.setBirthday(LocalDate.of(1956, 10, 12));

        userService.addUsers(user);
        userService.addUsers(userFriend);
        userService.addUsers(userFriend2);
        userService.addFriend(user.getId(), userFriend.getId());
        userService.deleteFriend(user.getId(), userFriend2.getId());
        long expected = 1;
        Assertions.assertEquals(expected, user.getFriends().size());
    }
}
