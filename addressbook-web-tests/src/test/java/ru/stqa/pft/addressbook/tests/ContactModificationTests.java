package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().baseURL();
    if (app.contact().all().size() == 0) {
      app.contact().create(new ContactData().withFirstName("Test 1"));
    }
  }

  @Test
  public void testContactModification() {
    Contacts before = app.contact().all();
    ContactData modifiedContact = before.any();
    ContactData contact = new ContactData()
            .withId(modifiedContact.getId())
            .withFirstName("Edit 1")
            .withLastName("Edit 2")
            .withAddress("Edit 3")
            .withPhoneHome("+79666666666")
            .withEmail("edit@test.com");
    app.contact().modify(contact);
    assertThat("Test contacts quantity.", app.contact().count(), equalTo(before.size()));
    Contacts after = app.contact().all();
    assertThat("Test contacts content.", after, equalTo(before.without(modifiedContact).withAdded(contact)));
  }
}
