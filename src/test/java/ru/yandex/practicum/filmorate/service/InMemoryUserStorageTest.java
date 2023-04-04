package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.time.LocalDate;

class InMemoryUserStorageTest {
    private static InMemoryUserStorage inMemoryUserStorage;

    @BeforeEach
    public void beforeEach() {
        inMemoryUserStorage = new InMemoryUserStorage();
    }

    // тест updateUser()
    @Test
    public void  errorCheckingIfTheIdIsNotCorrectFromUser() {
        User user2 = User.builder()
                .id(5)
                .email("чебурек1@mail.ru")
                .login("чебурек1")
                .birthday(LocalDate.of(1991, 6, 28))
                .build();
        Assertions.assertThrows(ValidationException.class, () -> inMemoryUserStorage.update(user2));
    }

    // тесты checkUserName()
    @Test
    public void  errorCheckingIfTheIdIsNotCorrectLogin() {
        User user2 = User.builder()
                .email("чебурек2@mail.ru")
                .login("чебурек2")
                .birthday(LocalDate.of(1991, 6, 28))
                .build();
        inMemoryUserStorage.create(user2);
        Assertions.assertEquals("чебурек2", user2.getName());
    }
}