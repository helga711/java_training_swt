package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() {
    List<ContactData> before = app.getContactHelper().getContactList();
    ContactData contactData = new ContactData("Test 1", "Test 2", "Test 3", "+79655555555", "test@test.com");
    app.getNavigationHelper().goToNewContactPage();
    app.getContactHelper().createContact(contactData);
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size() + 1, "Contacts' quantity is invalid after creation of the contact.");

    before.add(contactData);
    Comparator<ContactData> byId = Comparator.comparingInt(ContactData::getId);
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(after, before, "Contacts' list is invalid after creation of the contact.");
  }
}
