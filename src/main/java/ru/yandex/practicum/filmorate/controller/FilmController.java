package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeption.ControllersExeption;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public List<Film> getFilms() {
        return filmService.allFilms();
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) throws ValidationException {
        log.info("Получен запрос на добавление фильма: {}", film.getName());
        return filmService.addFilm(film);
    }

    @PutMapping
    Film updateFilm(@Valid @RequestBody Film film) throws ControllersExeption {
        log.info("Получен запрос на обновление информации фильма: {}", film.getName());
        return filmService.updateFilm(film);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String validationError(final ValidationException e) {
        return "Ошибка: " + e.getMessage();
    }
}