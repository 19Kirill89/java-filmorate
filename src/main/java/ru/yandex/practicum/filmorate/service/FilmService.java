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
        log.debug("Получен запрос на добавление фильма: {}", film.getName());
        return inMemoryFilmStorage.createFilm(film);
    }

    public Film updateFilm(Film film) throws ControllersException {
        validateReleaseDate(film);
        log.info("Получен запрос на обновление информации фильма: {}", film.getName());
        return inMemoryFilmStorage.updateFilmInfo(film);
    }

    public void deleteAllFilms() {
        inMemoryFilmStorage.deleteAllFilms();
    }

    public Film filmById(long id) throws NotFound {
        log.debug("GET film");
        return inMemoryFilmStorage.getFilmById(id);
    }

    public void addLikeToFilm(long filmId, long userId) {
        Film film = inMemoryFilmStorage.getFilmById(filmId);
        User user = inMemoryUserStorage.getUserById(userId);
        if (user == null) {
            throw new NotFound("нет пользователя с id: " + userId);
        } else if (film == null) {
            throw new NotFound("нет фильма с id: " + filmId);
        } else {
            inMemoryFilmStorage.getFilmById(filmId).getLikes().add(userId);
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
            log.debug("удален лайк");
            inMemoryFilmStorage.getFilmById(filmId).getLikes().add(userId);
            inMemoryFilmStorage.updateFilmInfo(film);
        }
    }

    public List<Film> getTopFilms(Integer count) {
        log.debug("GET 10 популярных кинчиков");
        return inMemoryFilmStorage.getAllFilms().stream()
                .sorted((f1, f2) -> f2.getLikes().size() - f1.getLikes().size())
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

