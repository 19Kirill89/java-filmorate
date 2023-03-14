package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeption.NotFound;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> filmHashMap = new HashMap<>();
    private long idCounter;

    private final LocalDate FIRST_FILM_RELEASE_DATE = (LocalDate.of(1895, 12, 28));

    @Override
    public Film createFilm(Film film) throws ValidationException {
        validateReleaseDate(film);
        film.setId(++idCounter);
        filmHashMap.put(film.getId(), film);
        log.debug("POST запрос на добавление нового фильма - готово");
        return film;
    }

    @Override
    public Film updateFilmInfo(Film film) throws ValidationException {
        validateReleaseDate(film);
        if (!filmHashMap.containsKey(film.getId())) {
            throw new NotFound("нет фильма с ID " + film.getId());
        } else {
            filmHashMap.put(film.getId(), film);
        }
        log.debug("PUT запрос: фильм обновлен");
        return film;
    }

    @Override
    public Film getFilmById(Long id) throws NotFound {
        if (!filmHashMap.containsKey(id)) {
            throw new NotFound("нет фильма с id: " + id);
        } else {
            return filmHashMap.get(id);
        }
    }

    @Override
    public List<Film> getAllFilms() {
        return new ArrayList<>(filmHashMap.values());
    }

    @Override
    public void deleteAllFilms() {
        log.info("все фильмы удалены");
        filmHashMap.clear();
    }
    private void validateReleaseDate(Film film) {
        if (film.getReleaseDate().isBefore(FIRST_FILM_RELEASE_DATE)) {
            throw new ValidationException("Ошибка даты: исправьте дату релиза фильма, начало отсчета :" +
                    FIRST_FILM_RELEASE_DATE);
        }
    }
}