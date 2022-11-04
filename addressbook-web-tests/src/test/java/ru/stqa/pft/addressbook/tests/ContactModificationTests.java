package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification() {
    if (!app.getContactHelper().isThereAContact()) {
      ContactData contactData = new ContactData("Test 1", "Test 2", "Test 3", "+79655555555", "test@test.com");
      app.getNavigationHelper().goToNewContactPage();
      app.getContactHelper().createContact(contactData);
    }

    List<ContactData> before = app.getContactHelper().getContactList();
    app.getContactHelper().selectContact(before.size() - 1);
    app.getContactHelper().initContactModification();
    ContactData contactData = new ContactData("Edit 1", "Edit 2", "Edit 3", "+79666666666", "edit@test.com");
    app.getContactHelper().fillContactForm(contactData);
    app.getContactHelper().submitContactModification();
    app.getContactHelper().returnToHomePage();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size(), "Contacts' quantity is invalid after modification of the contact.");

    before.remove(before.size() - 1);
    before.add(contactData);
    Comparator<ContactData> byId = Comparator.comparingInt(ContactData::getId);
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(after, before, "Contacts' list is invalid after modification of the contact.");
  }
}
