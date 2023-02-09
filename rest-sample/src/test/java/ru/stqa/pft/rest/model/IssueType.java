package ru.stqa.pft.rest.model;

import java.util.Objects;

public class IssueType {
    private int id;

    public int getId() {
        return id;
    }

    public IssueType withId(int id) {
        this.id = id;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IssueType issueType = (IssueType) o;
        return id == issueType.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "IssueType{" +
                "id=" + id +
                '}';
    }
}
