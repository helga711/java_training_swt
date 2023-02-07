package ru.stqa.pft.addressbook.model;

import com.google.common.collect.ForwardingSet;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Groups extends ForwardingSet<GroupData> {

    private final Set<GroupData> delegate;

    public Groups(@NotNull Groups groups) {
        this.delegate = new HashSet<>(groups.delegate);
    }

    public Groups(@NotNull Collection<GroupData> groups) {
        this.delegate = new HashSet<>(groups);
    }

    public Groups() {
        this.delegate = new HashSet<>();
    }

    @Override
    protected @NotNull Set<GroupData> delegate() {
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
        return delegate.stream().mapToInt(GroupData::getId).max().orElse(0);
    }

    public GroupData any() {
        return delegate.iterator().next();
    }
    public Set<GroupData> toUI(){
        Set<GroupData> uiSet = delegate.stream().map((g)
                -> new GroupData().withId(g.getId()).withName(g.getName())).collect(Collectors.toSet());
        return new Groups(uiSet);
    }
}
