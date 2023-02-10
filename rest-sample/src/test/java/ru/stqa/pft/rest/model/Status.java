package ru.stqa.pft.rest.model;

public class Status {
    public int id;
    public String name;

    public int getId() {
        return id;
    }

    public Status withId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Status withName(String name) {
        this.name = name;
        return this;
    }
}
