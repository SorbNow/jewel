package org.jewel.web;

import org.jewel.model.User;

import java.util.List;

public class LoginFormData {
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
