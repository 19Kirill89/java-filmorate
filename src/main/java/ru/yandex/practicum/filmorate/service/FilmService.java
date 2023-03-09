package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.ControllersExeption;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class FilmService {
    private final HashMap<Long, Film> filmHashMap = new HashMap<>();
    private long idCounter;
    private final LocalDate FIRST_FILM_RELEASE_DATE = (LocalDate.of(1895, 12, 28));

    public List<Film> allFilms() {
        return new ArrayList<>(filmHashMap.values());
    }

    public Film addFilm(Film film) throws ValidationException {
        validateReleaseDate(film);
        film.setId(++idCounter);
        filmHashMap.put(film.getId(), film);
        log.debug("POST запрос на добавление нового фильма - готово");
        return film;
    }

    public Film updateFilm(Film film) throws ControllersExeption {
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