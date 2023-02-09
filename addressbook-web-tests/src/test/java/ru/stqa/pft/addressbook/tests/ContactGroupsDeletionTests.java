package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactGroupsDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() throws Exception {
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("Test 1"));
        }

        app.goToBaseURL();
        if (app.db().contacts().withGroups().size() == 0) {
            GroupData group = app.db().groups().any();
            app.goTo().newContactPage();
            app.contact().create(new ContactData()
                    .withFirstName("Test 1")
                    .withLastName("Test 2")
                    .inGroup(group));
        }
    }

    @Test
    public void testDeletingContactFromGroups(){
        Contacts before = app.db().contacts();
        ContactData modifiedContact = before.withGroups().any();
        ContactData contact = modifiedContact.copy().withoutGroups(modifiedContact.groups);
        app.contact().DeleteFromGroups(contact, modifiedContact.groups);
        Contacts after = app.db().contacts();
        assertThat("Test contacts content.", after, equalTo(before.without(modifiedContact).withAdded(contact)));
    }
}
