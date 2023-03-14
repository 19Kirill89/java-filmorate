package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserStorageTest {
    private InMemoryUserStorage inMemoryUserStorage;

    @BeforeEach
    public void setUserController() {
        inMemoryUserStorage = new InMemoryUserStorage();
    }

    @Test
    public void showResultOfAddUser() {
        User user1 = new User();
        user1.setEmail("ffff@gmail.com");
        user1.setLogin("M-9");
        user1.setBirthday(LocalDate.of(1956, 10, 12));

        User user2 = new User();
        user2.setName("Пчела");
        user2.setEmail("afafaf1214@gmail.com");
        user2.setLogin("ПЧ-9");
        user2.setBirthday(LocalDate.of(1956, 10, 12));
        inMemoryUserStorage.createUser(user1);
        inMemoryUserStorage.createUser(user2);
        assertEquals(user1.getName(), user1.getLogin());
        List<User> expected = List.of(user1, user2);
        List<User> actual = inMemoryUserStorage.getAllUser();
        assertEquals(expected, actual);
    }

    @Test
    public void resultOfUpdateUser() {
        String userNameTest = "Беляш";
        User user1 = new User();
        user1.setEmail("3456jj@gmail.com");
        user1.setLogin("M-9");
        user1.setBirthday(LocalDate.of(1956, 10, 12));

        User user2 = new User();
        user2.setName("Пчела");
        user2.setEmail("afafaf1214@gmail.com");
        user2.setLogin("ПЧ-9");
        user2.setBirthday(LocalDate.of(1956, 10, 12));


        inMemoryUserStorage.createUser(user1);
        inMemoryUserStorage.createUser(user2);
        user1.setName(userNameTest);
        inMemoryUserStorage.updateUser(user1);
        assertEquals(userNameTest, user1.getName());
        List<User> expected = List.of(user1, user2);
        List<User> actual = inMemoryUserStorage.getAllUser();
        assertEquals(expected, actual);
    }
}
