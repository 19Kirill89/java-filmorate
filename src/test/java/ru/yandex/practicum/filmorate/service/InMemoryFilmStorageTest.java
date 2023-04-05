package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.time.LocalDate;

class InMemoryFilmStorageTest {
    private static InMemoryFilmStorage inMemoryFilmStorage;

    @BeforeEach
    public void beforeEach() {
        inMemoryFilmStorage = new InMemoryFilmStorage();
    }

    @Test
    public void errorCheckingIfTheIdIsNotCorrect() {
        Film film = Film.builder()
                .id(5)
                .name("А зори здесь тихие")
                .description("Война")

                .releaseDate(LocalDate.of(1999, 9, 9))
                .duration(99)
                .build();
        Assertions.assertThrows(ValidationException.class, () -> inMemoryFilmStorage.updateFilm(film));
    }
}