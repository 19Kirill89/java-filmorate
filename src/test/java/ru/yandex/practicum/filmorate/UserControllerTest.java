package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exeption.ControllersExeption;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserControllerTest {
    private UserController userController;

    @BeforeEach
    public void setUserController() {
        userController = new UserController();
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
        userController.addUsers(user1);
        userController.addUsers(user2);
        assertEquals(user1.getName(), user1.getLogin());
        List<User> expected = List.of(user1, user2);
        List<User> actual = userController.allUsers();
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
        userController.addUsers(user1);
        userController.addUsers(user2);
        user1.setName(userNameTest);
        userController.updateUser(user1);
        assertEquals(userNameTest, user1.getName());
        List<User> expected = List.of(user1, user2);
        List<User> actual = userController.allUsers();
        assertEquals(expected, actual);
    }
}
