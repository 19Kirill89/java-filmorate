package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeption.ControllersException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.List;


@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final HashMap<Long, Film> filmHashMap = new HashMap<>();
    private long idCounter;

    @Override
    public Film createFilm(Film film) {
        film.setId(++idCounter);
        filmHashMap.put(film.getId(), film);
        return film;
    }

    @Override
    public Film updateFilmInfo(Film film) {
        if (!filmHashMap.containsKey(film.getId())) {
            throw new ControllersException("нет фильма с ID " + film.getId());
        } else {
            filmHashMap.put(film.getId(), film);
        }
        return film;
    }

    @Override
    public Film getFilmById(Long id) {
        if (!filmHashMap.containsKey(id)) {
            throw new ControllersException("нет фильма с id: " + id);
        } else {
            return filmHashMap.get(id);
        }
    }

    @Override
    public List<Film> getAllFilms() {
        if (filmHashMap.isEmpty()) {
            throw new ControllersException("в данный момент фильмов нет");
        } else {
            return (List<Film>) filmHashMap.values();
        }
    }

    @Override
    public void deleteAllFilms() {
        filmHashMap.clear();
    }
}