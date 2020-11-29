package com.ivangy.lospaco.model;

public class Role {

    private int Id;
    private String Name;

    public Role(int id, String name) {
        Id = id;
        Name = name;
    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }
}
