package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.List;


class FilmMemoryTest {
    private InMemoryFilmStorage inMemoryFilmStorage;

    @BeforeEach
    public void filmControllerInit() {
        inMemoryFilmStorage = new InMemoryFilmStorage();
    }

    @Test
    public void showResultAddFilm() {
        Film film1 = new Film();
        film1.setName("Анна Каренина");
        film1.setDescription("фильм про даму легкого поведения");
        film1.setDuration(20);
        film1.setReleaseDate(LocalDate.of(1338, 12, 1));
        Film film2 = new Film();
        film2.setName("какой-то фильм");
        film2.setDescription("про что-то");
        film2.setDuration(20);
        film2.setReleaseDate(LocalDate.of(1999, 2, 1));
        Assertions.assertThrows(ValidationException.class, () -> inMemoryFilmStorage.createFilm(film1));
        Assertions.assertDoesNotThrow(() -> inMemoryFilmStorage.createFilm(film2));
    }

    @Test
    public void shouResultOfUpdateFilm() {
        Film film1 = new Film();
        film1.setName("Анна Каренина");
        film1.setDescription("фильм про даму легкого поведения");
        film1.setDuration(20);
        film1.setReleaseDate(LocalDate.of(2008, 12, 1));
        Film film2 = new Film();
        film2.setName("какой-то фильм");
        film2.setDescription("про что-то");
        film2.setDuration(20);
        film2.setReleaseDate(LocalDate.of(1999, 2, 1));

        inMemoryFilmStorage.createFilm(film1);
        inMemoryFilmStorage.createFilm(film2);
        film2.setName("проект Атомное сердце");
        inMemoryFilmStorage.updateFilmInfo(film2);
        List<Film> expected = List.of(film1, film2);
        List<Film> actual = inMemoryFilmStorage.getAllFilms();
        Assertions.assertEquals(expected, actual);
    }
}
