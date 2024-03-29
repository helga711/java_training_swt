package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() throws Exception {
    app.goToBaseURL();
    if (app.db().contacts().size() == 0) {
      app.goTo().newContactPage();
      app.contact().create(new ContactData().withFirstName("Test 1"));
    }
  }

  @Test
  public void testContactDeletion(){
    Contacts before = app.db().contacts();
    ContactData deletedContact = before.any();
    app.contact().delete(deletedContact);
    app.goToBaseURL();
    assertThat("Test contacts quantity.", app.contact().count(), equalTo(before.size() - 1));
    Contacts after = app.db().contacts();
    assertThat("Test contacts content.", after, equalTo(before.without(deletedContact)));
    verifyContactListInUI();
  }
}
