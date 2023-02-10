package ru.stqa.pft.mantis.model;

import java.math.BigInteger;

public class Project {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public Project withId(BigInteger id) {
        this.id = id.intValue();
        return this;
    }

    public Project withId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Project withName(String name) {
        this.name = name;
        return this;
    }
}
