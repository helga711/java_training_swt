package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupModificationTests extends TestBase{

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().groupPage();
        if (app.group().all().size() == 0) {
            app.group().create(new GroupData().withName("Test 1"));
        }
    }

    @Test
    public void testGroupModification(){
        Groups before = app.group().all();
        GroupData modifiedGroup = before.any();
        GroupData group = new GroupData()
                .withId(modifiedGroup.getId())
                .withName("edit1")
                .withHeader("edit2")
                .withFooter("edit3");
        app.group().modify(group);
        assertThat("Test groups quantity.", app.group().count(), equalTo(before.size()));
        Groups after = app.group().all();
        assertThat("Test groups content.", after, equalTo(before.without(modifiedGroup).withAdded(group)));
    }
}
