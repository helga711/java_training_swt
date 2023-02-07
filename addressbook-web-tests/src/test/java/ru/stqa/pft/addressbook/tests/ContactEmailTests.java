package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactEmailTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goToBaseURL();
    if (app.contact().all().withEmails().size() == 0) {
      app.goTo().newContactPage();
      app.contact().create(new ContactData()
              .withFirstName("Test 1")
              .withLastName("Test 2")
              .withEmail("test@test.com")
              .withEmail2("test.tst@test.tst.com")
              .withEmail3("test3@test.com"));
    }
  }

  @Test
  public void testContactEmails() {
    ContactData contact = app.contact().all().withEmails().any();
    ContactData contactFromEditForm = app.contact().infoFromEditForm(contact.getId());
    assertThat(String.format("Test emails for %s", contact), contact.getAllEmails(), equalTo(contactFromEditForm.getAllEmails()));
  }
}
