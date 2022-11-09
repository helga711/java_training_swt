package ru.stqa.pft.addressbook.model;

import com.google.common.collect.ForwardingSet;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class Contacts extends ForwardingSet<ContactData> {

    private Set<ContactData> delegate;

    public Contacts(@NotNull Contacts contacts) {
        this.delegate = new HashSet<ContactData>(contacts.delegate);
    }

    public Contacts() {
        this.delegate = new HashSet<ContactData>();
    }

    @Override
    protected Set<ContactData> delegate() {
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
        return delegate.stream().mapToInt((g) -> g.getId()).max().getAsInt();
    }

    public ContactData any() {
        return delegate.iterator().next();
    }
}
