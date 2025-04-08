package ru.kata.spring.boot_security.demo.util;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public class AdminResponse {
    private final User authUser;
    private final List<User> users;
    private final List<?> allRoles;
    private final User editUser;
    private final User deleteUser;

    public AdminResponse(User authUser, List<User> users, List<?> allRoles, User editUser, User deleteUser) {
        this.authUser = authUser;
        this.users = users;
        this.allRoles = allRoles;
        this.editUser = editUser;
        this.deleteUser = deleteUser;
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

    public User getEditUser() {
        return editUser;
    }

    public User getDeleteUser() {
        return deleteUser;
    }
}
