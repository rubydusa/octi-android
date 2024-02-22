package com.example.octi.Models;

public class User {
    String email;
    String name;
    String id;

    public User(String email, String name, String id) {
        this.email = email;
        this.name = name;
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }
}
