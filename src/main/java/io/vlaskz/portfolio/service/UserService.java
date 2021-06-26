package io.vlaskz.portfolio.service;

import io.vlaskz.portfolio.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class UserService {

    private static List<User> userList;
    static{

        userList = new ArrayList<>(List.of(
                new User(1L, "Isaias Velasquez", "vlaskz@icloud.com"),
                new User(2L, "Doritos Velasquez", "doritos@icloud.com")));
    }


    public List<User> list() {
        return userList;
    }

    public User findUserById(Long id) {
        return userList
                .stream()
                .filter(User -> Objects.equals(User.getId(), id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));
    }

    public User save(User user) {

        user.setId(ThreadLocalRandom.current().nextLong());
        userList.add(user);
        return user;
    }

}
