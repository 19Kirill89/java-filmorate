package ru.yandex.practicum.filmorate;

import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.exeption.ControllersExeption;
import java.time.LocalDate;
import java.util.List;


class FilmControllerTest {
    private FilmController filmController;

    @BeforeEach
    public void filmControllerInit() {
        filmController = new FilmController();
    }

    @Test
    public void showResultAddFilm() {
        Film film1 = Film.builder()
                .name("Анна Каренина")
                .description("фильм про даму легкого поведения")
                .duration(20)
                .releaseDate(LocalDate.of(1338,12,1))
                .build();
        Film film2 = Film.builder()
                .name("какой-то фильм")
                .description("про что-то")
                .duration(20)
                .releaseDate(LocalDate.of(1999,2,1))
                .build();

        Assertions.assertThrows(ValidationException.class, () -> filmController.addFilm(film1));
        Assertions.assertDoesNotThrow(() -> filmController.addFilm(film2));
    }
    @Test
    public void shouResultOfUpdateFilm() throws ControllersExeption {
        Film film1 = Film.builder()
                .name("Анна Каренина")
                .description("гуляла по мужикам, сиганула под поезд")
                .duration(20)
                .releaseDate(LocalDate.of(2003,12,1))
                .build();
        Film film2 = Film.builder()
                .name("какой-то фильм")
                .description("боевик")
                .duration(20)
                .releaseDate(LocalDate.of(1999,2,1))
                .build();

        filmController.addFilm(film1);
        filmController.addFilm(film2);
        film2.setName("проект Атомное сердце");
        filmController.updateFilm(film2);
        List<Film> expected = List.of(film1, film2);
        List<Film> actual = filmController.allFilms();
        Assertions.assertEquals(expected, actual);
    }
}