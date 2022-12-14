package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactPhoneTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().baseURL();
    if (app.contact().all().withPhones().size() == 0) {
      app.goTo().newContactPage();
      app.contact().create(new ContactData()
              .withFirstName("Test 1")
              .withLastName("Test 2")
              .withPhoneHome("+7 (111)")
              .withPhoneMobile("22-22")
              .withPhoneWork("333 333"));
    }
  }

  @Test
  public void testContactPhones() {
    ContactData contact = app.contact().all().withPhones().any();
    ContactData contactFromEditForm = app.contact().infoFromEditForm(contact.getId());
    assertThat(String.format("Test phones for %s", contact), contact.getAllPhones(), equalTo(contactFromEditForm.getAllPhones()));
  }
}
