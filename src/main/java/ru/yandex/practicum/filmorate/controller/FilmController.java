package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeption.ControllersException;
import ru.yandex.practicum.filmorate.exeption.ErrorResponse;
import ru.yandex.practicum.filmorate.exeption.NotFound;
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
        return filmService.getAllFilms();
    }

    @GetMapping("/{id}")
    public Film getFilm(@PathVariable Integer id) throws NotFound {
        return filmService.filmById(id);
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) throws ValidationException {
        return filmService.addFilm(film);
    }

    @PutMapping
    Film updateFilm(@Valid @RequestBody Film film) throws ValidationException {
        return filmService.updateFilm(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public void putLike(@PathVariable Integer id, @PathVariable Integer userId) throws NotFound {
        filmService.addLikeToFilm(id, userId);
    }

    @DeleteMapping("{id}/like/{userId}")
    public void removeLikeFilm(@PathVariable long filmId, @PathVariable long userId)
    throws NotFound {
        log.info("DELETE запрос лайка к фильму id = {} от пользователя id = {}", filmId, userId);
        filmService.deleteFilmLikes(filmId, userId);
    }

    @GetMapping("/popular")
    public List<Film> getMostPopularFilms(@RequestParam(defaultValue = "10") String count) {
        int intCount = Integer.parseInt(count);
        return filmService.getTopFilms(intCount);
    }
}