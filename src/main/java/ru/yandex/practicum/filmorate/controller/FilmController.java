package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.exeption.ControllersExeption;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final HashMap<Long, Film> filmHashMap = new HashMap<>();
    private long idCounter;
    private final LocalDate FIRST_FILM_RELEASE_DATE = (LocalDate.of(1895, 12, 28));

    @GetMapping
    public List<Film> allFilms() {
        return new ArrayList<>(filmHashMap.values());
    }


    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) throws ValidationException {
        validateReleaseDate(film);
        film.setId(++idCounter);
        filmHashMap.put(film.getId(), film);
        log.debug("POST запрос на добавление нового фильма - готово");
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) throws ControllersExeption {
        validateReleaseDate(film);
        if (!filmHashMap.containsKey(film.getId())) {
            throw new ControllersExeption("нет фильма с ID " + film.getId());
        } else {
            filmHashMap.put(film.getId(), film);
            log.debug("PUT запрос: фильм обновлен");
        }
        return film;
    }

    private void validateReleaseDate(Film film) {
        if (film.getReleaseDate().isBefore(FIRST_FILM_RELEASE_DATE)) {
            throw new ValidationException("Ошибка даты: исправьте дату релиза фильма, начало отсчета :" +
                    FIRST_FILM_RELEASE_DATE);
        }
    }
}
