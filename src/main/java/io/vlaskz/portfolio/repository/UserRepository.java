package io.vlaskz.portfolio.repository;


import io.vlaskz.portfolio.model.User;

import java.util.List;

public interface UserRepository {

    List<User> list();
    User findUserById(Long id);

}
