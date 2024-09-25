package com.project.planetarium.repository.user;

import java.util.Optional;

import com.project.planetarium.entities.User;

public interface UserDao {

    Optional<User> createUser(User newUser);

    Optional<User> findUserByUsername(String username);
}
