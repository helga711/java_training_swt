package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactGroupsTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() throws Exception {
        int groupCount = app.db().groups().size();
        for (int i = groupCount; i < 2; i++) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("Test " + i));
        }

        app.goTo().baseURL();
        if (app.db().contacts().withoutGroups().size() == 0) {
            app.goTo().newContactPage();
            app.contact().create(new ContactData()
                    .withFirstName("Test 1")
                    .withLastName("Test 2"));
        }
    }

    @Test
    public void testAddingContactToGroups(){
        Contacts before = app.db().contacts();
        Groups groups = app.db().groups();
        GroupData group = groups.any();
        GroupData group2 = groups.without(group).any();
        ContactData modifiedContact = before.withoutGroups().any();
        ContactData contact = modifiedContact.copy().inGroup(group).inGroup(group2);
        app.goTo().baseURL();
        app.contact().AddToGroups(contact, new Groups().withAdded(group).withAdded(group2));
        Contacts after = app.db().contacts();
        assertThat("Test contacts content.", after, equalTo(before.without(modifiedContact).withAdded(contact)));
    }
}
