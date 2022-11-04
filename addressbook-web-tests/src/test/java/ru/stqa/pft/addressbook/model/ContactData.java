package ru.stqa.pft.addressbook.model;

import java.util.Objects;

public class ContactData {
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String phoneHome;
    private final String email;

    private int id;

    public ContactData(String firstName, String lastName, String address, String phoneHome, String email) {
        this.id = Integer.MAX_VALUE;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneHome = phoneHome;
        this.email = email;
    }

    public ContactData(int id, String firstName, String lastName, String address, String phoneHome, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneHome = phoneHome;
        this.email = email;
    }

    public ContactData(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = null;
        this.phoneHome = null;
        this.email = null;
    }

    public String getFirstName() {

        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneHome() {
        return phoneHome;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ContactData{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactData that = (ContactData) o;
        return Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }
}
