package com.project.planetarium.service.user;

import com.project.planetarium.entities.User;

public interface UserService {

    public String createUser(User newUser);

    public User authenticate(User credentials);

}
