package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification() {
    app.getContactHelper().selectContact();
    app.getContactHelper().initContactModification();
    ContactData contactData = new ContactData("Edit 1", "Edit 2", "Edit 3", "+79666666666", "edit@test.com");
    app.getContactHelper().fillContactForm(contactData);
    app.getContactHelper().submitContactModification();
    app.getContactHelper().returnToHomePage();
  }
}
