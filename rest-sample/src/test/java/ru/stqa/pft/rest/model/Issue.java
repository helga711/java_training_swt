package ru.stqa.pft.rest.model;

import java.util.Objects;

public class Issue {
    private int id;
    private IssueFields fields;
    private String key;

    public int getId() {
        return id;
    }

    public Issue withId(int id) {
        this.id = id;
        return this;
    }

    public IssueFields getFields() {
        return fields;
    }

    public Issue withFields(IssueFields fields) {
        this.fields = fields;
        return this;
    }

    public String getKey() {
        return key;
    }

    public Issue withKey(String key) {
        this.key = key;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Issue issue = (Issue) o;
        return id == issue.id && Objects.equals(fields, issue.fields) && Objects.equals(key, issue.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fields, key);
    }

    @Override
    public String toString() {
        return "Issue{" +
                "id=" + id +
                ", fields=" + fields +
                ", key='" + key + '\'' +
                '}';
    }
}
