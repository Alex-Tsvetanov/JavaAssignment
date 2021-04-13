package com.alextsvetanov.REST.API;

public class Person implements IPerson {
    private int id;
    private String name;

    public Person (int id, String name) {
        this.setId(id);
        this.setName(name);
    }

    public void setId (int id) { this.id = id; }
    public void setName (String name) { this.name = name; }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }
}
