package com.ivangy.lospaco.model;

import java.io.Serializable;

public class Category implements Serializable {
    private short Id;
    private String Name;

    public Category(short id, String name) {
        Id = id;
        Name = name;
    }

    public short getId() {
        return Id;
    }

    public void setId(short id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
