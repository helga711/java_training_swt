package ru.stqa.pft.mantis.model;

import java.math.BigInteger;

public class Status {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public Status withId(BigInteger id) {
        this.id = id.intValue();
        return this;
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

    @Override
    public String toString() {
        return "Status{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
