package ru.stqa.pft.addressbook.model;

import com.google.common.collect.ForwardingSet;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Contacts extends ForwardingSet<ContactData> {

    private final Set<ContactData> delegate;

    public Contacts(@NotNull Contacts contacts) {
        this.delegate = new HashSet<>(contacts.delegate);
    }

    public Contacts() {
        this.delegate = new HashSet<>();
    }

    public Contacts(Set<ContactData> contacts) {
        this.delegate = new HashSet<>(contacts);
    }

    public Contacts(Collection<ContactData> contacts) {
        this.delegate = new HashSet<>(contacts);
    }

    @Override
    protected @NotNull Set<ContactData> delegate() {
        return delegate;
    }

    public Contacts withAdded(ContactData contact) {
        Contacts contacts = new Contacts(this);
        contacts.add(contact);
        return contacts;
    }

    public Contacts without(ContactData contact) {
        Contacts contacts = new Contacts(this);
        contacts.remove(contact);
        return contacts;
    }

    public int maxId() {
        return delegate.stream().mapToInt(ContactData::getId).max().orElse(0);
    }

    public ContactData any() {
        return delegate.iterator().next();
    }

    public Contacts withPhones() {
        Set<ContactData> set = this.delegate.stream()
                .filter((contact) -> contact.getAllPhones() != null && !contact.getAllPhones().equals(""))
                .collect(Collectors.toSet());
        return new Contacts(set);
    }

    public Contacts withAddress() {
        Set<ContactData> set = this.delegate.stream()
                .filter((contact) -> contact.getAddress() != null && !contact.getAddress().equals(""))
                .collect(Collectors.toSet());
        return new Contacts(set);
    }

    public Contacts withEmails() {
        Set<ContactData> set = this.delegate.stream()
                .filter((contact) -> contact.getAllEmails() != null && !contact.getAllEmails().equals(""))
                .collect(Collectors.toSet());
        return new Contacts(set);
    }

    public Contacts toUI() {
        Set<ContactData> set = this.delegate.stream().map((c) -> new ContactData()
                        .withId(c.getId())
                        .withLastName(c.getLastName())
                        .withFirstName(c.getFirstName())
                        .withAddress(c.getAddress())
                        .withAllEmails(c.getAllEmails())
                        .withAllPhones(c.getAllPhones())).collect(Collectors.toSet());
        return new Contacts(set);
    }

    public Contacts withoutGroups() {
        Set<ContactData> set = delegate.stream()
                .filter((c) -> c.groups == null || c.groups.size() == 0)
                .collect(Collectors.toSet());
        return new Contacts(set);
    }
}
