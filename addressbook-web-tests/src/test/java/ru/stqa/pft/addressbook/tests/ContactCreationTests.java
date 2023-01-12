package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() {
    app.goTo().baseURL();
    Contacts before = app.contact().all();
    ContactData contact = new ContactData()
            .withFirstName("Test 1")
            .withLastName("Test 2")
            .withAddress("Test 3")
            .withPhoneHome("+79655555555")
            .withEmail("test@test.com");
    app.goTo().newContactPage();
    app.contact().create(contact);
    assertThat("Test contacts quantity.", app.contact().count(), equalTo(before.size() + 1));
    Contacts after = app.contact().all();
    assertThat("Test contacts content.", after, equalTo(before.withAdded(contact.withId(after.maxId()))));
  }
}
