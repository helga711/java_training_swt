package ru.stqa.pft.addressbook.model;

import com.google.gson.annotations.Expose;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.hibernate.annotations.Type;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.io.File;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@XStreamAlias("contact")
@Entity
@Table(name = "addressbook")
public class ContactData {
    @Expose
    @Column(name = "firstname")
    private String firstName;

    @Expose
    @Column(name = "lastname")
    private String lastName;

    @Expose
    @Column(name = "address")
    @Type(type = "text")
    private String address;

    @Expose
    @Column(name = "home")
    @Type(type = "text")
    private String phoneHome;

    @Expose
    @Column(name = "mobile")
    @Type(type = "text")
    private String phoneMobile;

    @Expose
    @Column(name = "work")
    @Type(type = "text")
    private String phoneWork;

    @Expose
    @Column(name = "email")
    @Type(type = "text")
    private String email;

    @Expose
    @Column(name = "email2")
    @Type(type = "text")
    private String email2;

    @Expose
    @Column(name = "email3")
    @Type(type = "text")
    private String email3;

    @XStreamOmitField
    @Transient
    private String allPhones;

    @XStreamOmitField
    @Transient
    private String allEmails;

    @XStreamOmitField
    @Column(name = "photo")
    @Type(type = "text")
    private String photo;

    @XStreamOmitField
    @Id
    @Column(name = "id")
    private int id = Integer.MAX_VALUE;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "address_in_groups", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
    public Set<GroupData> groups = new HashSet<>();

    public Groups getGroups() {
        return new Groups(groups);
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

    public File getPhoto() {
        if (photo == null) {
            return  null;
        }

        return new File(photo);
    }

    public ContactData withPhoto(File photo) {
        this.photo = photo.getPath();
        return this;
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

    public ContactData inGroup(GroupData group) {
        groups.add(group);
        return this;
    }

    public ContactData inGroups(Groups groups) {
        this.groups.addAll(groups);
        return this;
    }

    public ContactData withoutGroups(Set<GroupData> groups) {
        this.groups.removeAll(groups);
        return this;
    }

    private static String cleaned(@NotNull String phone) {
        return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
    }

    public ContactData toDB(){
        if (this.address == null) {
            this.address = "";
        }

        if (this.phoneHome == null) {
            this.phoneHome = "";
        }

        if (this.phoneMobile == null) {
            this.phoneMobile = "";
        }

        if (this.phoneWork == null) {
            this.phoneWork = "";
        }

        if (this.email == null) {
            this.email = "";
        }

        if (this.email2 == null) {
            this.email2 = "";
        }

        if (this.email3 == null) {
            this.email3 = "";
        }

        return this;
    }

    @Override
    public String toString() {
        StringBuilder toString = new StringBuilder();
        toString.append("ContactData{");
        toString.append(String.format("firstName='%s'", firstName));
        toString.append(String.format(", lastName='%s'", lastName));
        toString.append(String.format(", id='%s'", id));

        if (address != null && !address.equals("")){
            toString.append(String.format(", address='%s'", address));
        }
        if (getAllPhones() != null && !getAllPhones().equals("")){
            toString.append(String.format(", allPhones='%s'", allPhones));
        }
        if (getAllEmails() != null && !getAllEmails().equals("")){
            toString.append(String.format(", allEmails='%s'", allEmails));
        }
        if (groups != null && groups.size() > 0){
            toString.append(String.format(", groups='%s'", groups));
        }
        return toString.toString();
    }

    public ContactData copy(){
        return  new ContactData()
                .withId(id)
                .withFirstName(firstName)
                .withLastName(lastName)
                .withAddress(address)
                .withPhoneHome(phoneHome)
                .withPhoneMobile(phoneMobile)
                .withPhoneWork(phoneWork)
                .withEmail(email)
                .withEmail2(email2)
                .withEmail3(email3)
                .inGroups(new Groups(groups));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactData that = (ContactData) o;
        return id == that.id && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(address, that.address) && Objects.equals(phoneHome, that.phoneHome) && Objects.equals(phoneMobile, that.phoneMobile) && Objects.equals(phoneWork, that.phoneWork) && Objects.equals(email, that.email) && Objects.equals(email2, that.email2) && Objects.equals(email3, that.email3) && Objects.equals(groups, that.groups);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, address, phoneHome, phoneMobile, phoneWork, email, email2, email3, id, groups);
    }
}
