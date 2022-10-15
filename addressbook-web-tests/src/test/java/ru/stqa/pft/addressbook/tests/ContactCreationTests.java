package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() {
    ContactData contactData = new ContactData("Test 1", "Test 2", "Test 3", "+79655555555", "test@test.com");
    app.getNavigationHelper().goToNewContactPage();
    app.getContactHelper().createContact(contactData);
  }
}
