package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserDbStorageTest {
    private final UserDbStorage userDbStorage;
    private final JdbcTemplate jdbcTemplate;
    private User user;

    @BeforeEach
    void beforeEach() {
        jdbcTemplate.update("DELETE FROM users");
        user = User.builder()
                .email("nichaev@mail.ru")
                .login("ничаев")
                .birthday(LocalDate.of(1999, 1, 22))
                .friends(new HashSet<>())
                .build();
    }

    @Test
    void createUserAndGetUser() {
        userDbStorage.create(user);
        Assertions.assertEquals(user, userDbStorage.getUser(user.getId()));
    }

    @Test
    void getNameEmailUser() {
        userDbStorage.create(user);
        user.setEmail("ss@mail.ru");
        user.setName("gdg");
        userDbStorage.update(user);
        Assertions.assertEquals("ss@mail.ru", userDbStorage.getUser(user.getId()).getEmail());
        Assertions.assertEquals("gdg", userDbStorage.getUser(user.getId()).getName());
    }

    @Test
    void getUserList() {
        userDbStorage.create(user);
        userDbStorage.delete(user.getId());
        Assertions.assertEquals(new ArrayList<>(), userDbStorage.getUsers());
    }

    @Test
    void getUserFromDb() {
        userDbStorage.create(user);
        Assertions.assertEquals(user, userDbStorage.getUser(user.getId()));
    }

    @Test
    void addFriendAndShow() {
        User friend = User.builder()
                .email("koresh@mail.ru")
                .login("koresh")
                .birthday(LocalDate.of(1999, 1, 1))
                .friends(new HashSet<>())
                .build();
        userDbStorage.create(user);
        userDbStorage.create(friend);
        userDbStorage.addFriend(user.getId(), friend.getId());
        Assertions.assertEquals(1, userDbStorage.getFriends(user.getId()).size());
        Assertions.assertEquals(friend, userDbStorage.getFriends(user.getId()).toArray()[0]);
    }

    @Test
    void deleteFriend() {
        User friend = User.builder()
                .email("koresh@mail.ru")
                .login("koresh")
                .birthday(LocalDate.of(1991, 4, 27))
                .friends(new HashSet<>())
                .build();
        userDbStorage.create(user);
        userDbStorage.create(friend);
        userDbStorage.addFriend(user.getId(), friend.getId());
        userDbStorage.deleteFriend(user.getId(), friend.getId());
        Assertions.assertEquals(new ArrayList<>(), userDbStorage.getFriends(user.getId()));
    }

    @Test
    void shouldGetFriends() {
        User friend = User.builder()
                .email("friend@mail.ru")
                .login("friend")
                .birthday(LocalDate.of(1991, 4, 27))
                .friends(new HashSet<>())
                .build();
        User friend2 = User.builder()
                .email("friend2@mail.ru")
                .login("friend2")
                .birthday(LocalDate.of(1991, 4, 16))
                .friends(new HashSet<>())
                .build();
        userDbStorage.create(user);
        userDbStorage.create(friend);
        userDbStorage.create(friend2);
        userDbStorage.addFriend(user.getId(), friend.getId());
        userDbStorage.addFriend(user.getId(), friend2.getId());
        Assertions.assertEquals(2, userDbStorage.getFriends(user.getId()).size());
        Assertions.assertEquals(friend, userDbStorage.getFriends(user.getId()).toArray()[0]);
        Assertions.assertEquals(friend2, userDbStorage.getFriends(user.getId()).toArray()[1]);
    }

    @Test
    void getCommonFriends() {
        User friend = User.builder()
                .email("koresh@mail.ru")
                .login("koresh")
                .birthday(LocalDate.of(1991, 4, 7))
                .friends(new HashSet<>())
                .build();
        User friend2 = User.builder()
                .email("koresh2@mail.ru")
                .login("koresh2")
                .birthday(LocalDate.of(1999, 1, 6))
                .friends(new HashSet<>())
                .build();
        userDbStorage.create(user);
        userDbStorage.create(friend);
        userDbStorage.create(friend2);
        userDbStorage.addFriend(friend.getId(), user.getId());
        userDbStorage.addFriend(friend2.getId(), user.getId());
        Assertions.assertEquals(user, userDbStorage.getCommonFriends(friend.getId(), friend2.getId()).toArray()[0]);
    }
}