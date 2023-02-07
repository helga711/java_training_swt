package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddressTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() throws Exception {
    app.goToBaseURL();
    if (app.db().contacts().withAddress().size() == 0) {
      app.goTo().newContactPage();
      app.contact().create(new ContactData()
              .withFirstName("Test 1")
              .withLastName("Test 2")
              .withAddress("221B Baker Street\r\nMarylebone\r\nLondon UK"));
    }
  }

  @Test
  public void testContactAddress() {
    ContactData contact = app.db().contacts().withAddress().any();
    ContactData contactFromEditForm = app.contact().infoFromEditForm(contact.getId());
    assertThat(String.format("Test address for %s", contact), contact.getAddress(), equalTo(contactFromEditForm.getAddress()));
  }
}
