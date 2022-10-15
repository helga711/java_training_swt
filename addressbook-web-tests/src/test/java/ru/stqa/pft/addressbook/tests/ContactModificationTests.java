package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification() {
    if (!app.getContactHelper().isThereAContact()) {
      ContactData contactData = new ContactData("Test 1", "Test 2", "Test 3", "+79655555555", "test@test.com");
      app.getNavigationHelper().goToNewContactPage();
      app.getContactHelper().createContact(contactData);
    }
    app.getContactHelper().selectContact();
    app.getContactHelper().initContactModification();
    ContactData contactData = new ContactData("Edit 1", "Edit 2", "Edit 3", "+79666666666", "edit@test.com");
    app.getContactHelper().fillContactForm(contactData);
    app.getContactHelper().submitContactModification();
    app.getContactHelper().returnToHomePage();
  }
}
