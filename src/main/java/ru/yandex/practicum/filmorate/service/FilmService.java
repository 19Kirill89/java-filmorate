package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.GenreDbStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final GenreDbStorage genreDbStorage;

    public Film create(Film film) {
        return genreDbStorage.addFilmGenres(filmStorage.create(film));
    }

    public Film updateFilm(Film film) {
        return genreDbStorage.updateFilmGenres(filmStorage.updateFilm(film));
    }

    public Film delete(int filmId) {
        return filmStorage.delete(filmId);
    }

    public Film getFilm(int filmId) {
        return genreDbStorage.getFilmGenres(filmStorage.getFilm(filmId));
    }

    public Collection<Film> getFilms() {
        return genreDbStorage.getFilmsGenres(filmStorage.getFilms());
    }

    public Film addLike(int filmId, int userId) {
        userStorage.getUser(userId);
        return filmStorage.addLike(filmId, userId);
    }

    public Film removeLike(int filmId, int userId) {
        userStorage.getUser(userId);
        return filmStorage.removeLike(filmId, userId);
    }

    public List<Film> getPopularFilms(int count) {
        return genreDbStorage.getFilmsGenres(filmStorage.getPopularFilms(count));
    }
}
