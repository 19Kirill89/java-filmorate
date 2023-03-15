package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.NotFound;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public List<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    public Film addFilm(Film film) {
        log.debug("Получен запрос на добавление фильма: {}", film.getName());
        return filmStorage.createFilm(film);
    }

    public Film updateFilm(Film film) {
        log.info("Получен запрос на обновление информации фильма: {}", film.getName());
        return filmStorage.updateFilmInfo(film);
    }

    public void deleteAllFilms() {
        filmStorage.deleteAllFilms();
    }

    public Film filmById(long id) throws NotFound {
        log.debug("GET film");
        return filmStorage.getFilmById(id);
    }

    public void addLikeToFilm(long filmId, long userId) {
        Film film = filmStorage.getFilmById(filmId);
        User user = userStorage.getUserById(userId);
        if (user == null || user.getId() < 0) {
            throw new NotFound("нет пользователя с id: " + userId);
        } else if (film == null || film.getId() < 0) {
            throw new NotFound("нет фильма с id: " + filmId);
        } else {
            filmStorage.getFilmById(filmId).getLikes().add(userId);
        }
    }

    public void deleteFilmLikes(long filmId, long userId) {
        Film film = filmStorage.getFilmById(filmId);
        User user = userStorage.getUserById(userId);
        if (user == null || user.getId() < 0) {
            throw new NotFound("нет пользователя с id: " + userId);
        } else if (film == null || film.getId() < 0) {
            throw new NotFound("нет фильма с id: " + filmId);
        } else {
            log.debug("удален лайк");
            filmStorage.getFilmById(filmId).getLikes().remove(userId);
            filmStorage.updateFilmInfo(film);
        }
    }

    public List<Film> getTopFilms(Integer count) {
        log.debug("GET 10 популярных кинчиков");
        return filmStorage.getAllFilms().stream()
                .sorted((f1, f2) -> f2.getLikes().size() - f1.getLikes().size())
                .limit(count)
                .collect(Collectors.toList());
    }
}

