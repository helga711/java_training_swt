package ru.stqa.pft.addressbook.model;

import com.google.common.collect.ForwardingSet;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class Groups extends ForwardingSet<GroupData> {

    private Set<GroupData> delegate;

    public Groups(@NotNull Groups groups) {
        this.delegate = new HashSet<GroupData>(groups.delegate);
    }

    public Groups() {
        this.delegate = new HashSet<GroupData>();
    }

    @Override
    protected Set<GroupData> delegate() {
        return delegate;
    }

    public Groups withAdded(GroupData group) {
        Groups groups = new Groups(this);
        groups.add(group);
        return groups;
    }

    public Groups without(GroupData group) {
        Groups groups = new Groups(this);
        groups.remove(group);
        return groups;
    }

    public int maxId() {
        return delegate.stream().mapToInt((g) -> g.getId()).max().getAsInt();
    }

    public GroupData any() {
        return delegate.iterator().next();
    }
}
