/*
package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exeption.ControllersExeption;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserServiceTest {
    private UserService userService;

    @BeforeEach
    public void setUserController() {
        userService = new UserService();
    }

    @Test
    public void showResultOfAddUser() {
        User user1 = User.builder()
                .email("ffff@gmail.com")
                .login("M-9")
                .birthday(LocalDate.of(1956, 10, 12))
                .build();
        User user2 = User.builder()
                .name("Пчела")
                .email("afafaf1214@gmail.com")
                .login("ПЧ-9")
                .birthday(LocalDate.of(1956, 10, 12))
                .build();
        userService.addUsers(user1);
        userService.addUsers(user2);
        assertEquals(user1.getName(), user1.getLogin());
        List<User> expected = List.of(user1, user2);
        List<User> actual = userService.allUsers();
        assertEquals(expected, actual);
    }

    @Test
    public void resultOfUpdateUser() throws ControllersExeption {
        String userNameTest = "Беляш";
        User user1 = User.builder()
                .email("3456jj@gmail.com")
                .login("M-9")
                .birthday(LocalDate.of(1956, 10, 12))
                .build();
        User user2 = User.builder()
                .name("Пчела")
                .email("afafaf1214@gmail.com")
                .login("ПЧ-9")
                .birthday(LocalDate.of(1956, 10, 12))
                .build();
        userService.addUsers(user1);
        userService.addUsers(user2);
        user1.setName(userNameTest);
        userService.updateUser(user1);
        assertEquals(userNameTest, user1.getName());
        List<User> expected = List.of(user1, user2);
        List<User> actual = userService.allUsers();
        assertEquals(expected, actual);
    }
}
*/
