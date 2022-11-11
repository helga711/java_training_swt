package ru.stqa.pft.addressbook.model;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ContactData {
    private String firstName;
    private String lastName;
    private String address;
    private String phoneHome;
    private String phoneMobile;
    private String phoneWork;
    private String email;
    private String email2;
    private String email3;
    private String allPhones;
    private String allEmails;

    private int id = Integer.MAX_VALUE;

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

    public String getPhoneMobile() {
        return phoneMobile;
    }

    public String getPhoneWork() {
        return phoneWork;
    }

    public String getAllPhones() {
        if (allPhones == null || allPhones.equals("")) {
            allPhones = Stream.of(this.getPhoneHome(), this.getPhoneMobile(), this.getPhoneWork())
                    .filter((s) -> s != null && !s.equals(""))
                    .map(ContactData::cleaned)
                    .collect(Collectors.joining("\n"));
        }

        return allPhones;
    }

    public String getEmail2() {
        return email2;
    }

    public String getEmail3() {
        return email3;
    }

    public String getAllEmails() {
        if (allEmails == null || allEmails.equals("")) {
            allEmails = Stream.of(this.getEmail(), this.getEmail2(), this.getEmail3())
                    .filter((s) -> s != null && !s.equals(""))
                    .collect(Collectors.joining("\n"));
        }

        return allEmails;
    }

    public ContactData withId(int id) {
        this.id = id;
        return this;
    }

    public ContactData withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public ContactData withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public ContactData withAddress(String address) {
        this.address = address;
        return this;
    }

    public ContactData withPhoneHome(String phoneHome) {
        this.phoneHome = phoneHome;
        return this;
    }

    public ContactData withEmail(String email) {
        this.email = email;
        return this;
    }

    public ContactData withPhoneMobile(String phoneMobile) {
        this.phoneMobile = phoneMobile;
        return this;
    }

    public ContactData withPhoneWork(String phoneWork) {
        this.phoneWork = phoneWork;
        return this;
    }

    public ContactData withAllPhones(String allPhones) {
        this.allPhones = allPhones;
        return this;
    }

    public ContactData withEmail2(String email2) {
        this.email2 = email2;
        return this;
    }

    public ContactData withEmail3(String email3) {
        this.email3 = email3;
        return this;
    }

    public ContactData withAllEmails(String allEmails) {
        this.allEmails = allEmails;
        return this;
    }

    private static String cleaned(@NotNull String phone) {
        return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactData that = (ContactData) o;
        return id == that.id && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, id);
    }

    @Override
    public String toString() {
        return "ContactData{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", id=" + id +
                '}';
    }
}
