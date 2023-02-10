package ru.stqa.pft.mantis.model;

import javax.persistence.*;

@Entity
@Table(name = "mantis_user_table")
public class User {
    @Column(name = "username")
    private String username;

    @Transient
    private String mantisPassword;

    @Column(name = "email")
    private String email;

    @Id
    @Column(name = "id")
    private int id;

    public String getMailPassword() {
        return "password";
    }

    public String getUsername() {
        return username;
    }

    public User withUsername(String username) {
        this.username = username;
        return this;
    }

    public String getMantisPassword() {
        return mantisPassword;
    }

    public User withMantisPassword(String password) {
        this.mantisPassword = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User withEmail(String email) {
        this.email = email;
        return this;
    }

    public int getId() {
        return id;
    }

    public User withId(int id) {
        this.id = id;
        return this;
    }
}
