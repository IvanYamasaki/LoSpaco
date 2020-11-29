package com.ivangy.lospaco.model;

public class Account {
    public int Id;
    public String Email;
    public String Password;
    public Role Role;

    public Account(int id, String email, String password, Role role) {
        Id = id;
        Email = email;
        Password = password;
        Role = role;
    }

    public int getId() {
        return Id;
    }

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return Password;
    }

    public com.ivangy.lospaco.model.Role getRole() {
        return Role;
    }
}
