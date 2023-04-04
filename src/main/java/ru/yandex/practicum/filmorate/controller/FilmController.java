package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.IncorrectParameter;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.Collection;
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

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        log.info("POST запрос на добавление нового фильма.");
        return filmService.create(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        log.info("PUT запрос  на обновление фильма.");
        return filmService.update(film);
    }

    @DeleteMapping("/{id}")
    public Film delete(@PathVariable("id") int filmId) {
        return filmService.delete(filmId);
    }

    @GetMapping("/{id}")
    public Film getFilm(@PathVariable("id") int filmId) {
        return filmService.getFilm(filmId);
    }

    @GetMapping
    public Collection<Film> getFilms() {
        log.info("GET запрос на получение всех фильмов.");
        return filmService.getFilms();
    }

    @PutMapping("/{id}/like/{userId}")
    public Film addLike(@PathVariable("id") int filmId, @PathVariable("userId") int userId) {
        return filmService.addLike(filmId, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film removeLike(@PathVariable("id") int filmId, @PathVariable("userId") int userId) {
        return filmService.removeLike(filmId, userId);
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilms(@RequestParam(defaultValue = "10", required = false) int count) {
        if (count <= 0) {
            throw new IncorrectParameter("подсчет");
        }
        return filmService.getPopularFilms(count);
    }
}
