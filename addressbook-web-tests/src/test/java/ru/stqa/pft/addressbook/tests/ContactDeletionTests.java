package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import java.util.List;

public class ContactDeletionTests extends TestBase {

  @Test
  public void testContactDeletion() {
    if (!app.getContactHelper().isThereAContact()) {
      ContactData contactData = new ContactData("Test 1", "Test 2", "Test 3", "+79655555555", "test@test.com");
      app.getNavigationHelper().goToNewContactPage();
      app.getContactHelper().createContact(contactData);
    }

    List<ContactData> before = app.getContactHelper().getContactList();
    app.getContactHelper().selectContact(before.size() - 1);
    app.getContactHelper().deleteSelectedContact();
    app.getNavigationHelper().goToBaseURL();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size() - 1, "Contacts' quantity is invalid after deletion of the contact.");

    before.remove(before.size() - 1);
    Assert.assertEquals(after, before, "Contacts' list is invalid after deletion of the contact.");
  }
}
