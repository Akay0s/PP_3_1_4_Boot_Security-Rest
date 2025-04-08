package ru.kata.spring.boot_security.demo.util;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public class EditUserResponse {
    private final User authUser;
    private final List<User> users;
    private final List<?> allRoles;
    private final User user;

    public EditUserResponse(User authUser, List<User> users, List<?> allRoles, User user) {
        this.authUser = authUser;
        this.users = users;
        this.allRoles = allRoles;
        this.user = user;
    }

    public User getAuthUser() {
        return authUser;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<?> getAllRoles() {
        return allRoles;
    }

    public User getUser() {
        return user;
    }
}
