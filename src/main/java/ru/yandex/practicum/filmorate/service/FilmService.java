package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.ControllersException;
import ru.yandex.practicum.filmorate.exeption.NotFound;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService {
    private final InMemoryFilmStorage inMemoryFilmStorage;
    private final InMemoryUserStorage inMemoryUserStorage;

    @Autowired
    public FilmService(InMemoryFilmStorage inMemoryFilmStorage, InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    private final LocalDate FIRST_FILM_RELEASE_DATE = (LocalDate.of(1895, 12, 28));

    public List<Film> getAllFilms() {
        return inMemoryFilmStorage.getAllFilms();
    }

    public Film addFilm(Film film) throws ValidationException {
        validateReleaseDate(film);
        log.debug("POST запрос на добавление нового фильма - готово");
        return inMemoryFilmStorage.createFilm(film);
    }

    public Film updateFilm(Film film) throws ControllersException {
        validateReleaseDate(film);
        log.debug("PUT запрос: фильм обновлен");
        return inMemoryFilmStorage.updateFilmInfo(film);
    }

    public void deleteAllFilms() {
        log.info("все фильмы удалены");
        inMemoryFilmStorage.deleteAllFilms();
    }

    public Film filmById(long id) {
        return inMemoryFilmStorage.getFilmById(id);
    }

    public Film addLikeToFilm(long filmId, long userId) {
        Film film = inMemoryFilmStorage.getFilmById(filmId);
        User user = inMemoryUserStorage.getUserById(userId);
        if (user == null) {
            throw new NotFound("нет пользователя с id: " + userId);
        } else if (film == null) {
            throw new NotFound("нет фильма с id: " + filmId);
        } else {
            film.getLikes().add(userId);
            return inMemoryFilmStorage.updateFilmInfo(film);
        }
    }

    public void deleteFilmLikes(long filmId, long userId) {
        Film film = inMemoryFilmStorage.getFilmById(filmId);
        User user = inMemoryUserStorage.getUserById(userId);
        if (user == null) {
            throw new NotFound("нет пользователя с id: " + userId);
        } else if (film == null) {
            throw new NotFound("нет фильма с id: " + filmId);
        } else {
            film.getLikes().remove(userId);
            inMemoryFilmStorage.updateFilmInfo(film);
        }
    }

    public List<Film> getTopFilms(Integer count) {
        return inMemoryFilmStorage.getAllFilms().stream()
                .sorted((f1,f2) -> f2.getLikes().size() - f1.getLikes().size())
                .limit(count)
                .collect(Collectors.toList());
    }


    private void validateReleaseDate(Film film) {
        if (film.getReleaseDate().isBefore(FIRST_FILM_RELEASE_DATE)) {
            throw new ValidationException("Ошибка даты: исправьте дату релиза фильма, начало отсчета :" +
                    FIRST_FILM_RELEASE_DATE);
        }
    }
}

