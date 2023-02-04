package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.appmanager.FileExt;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validContactsFromXML() throws IOException {
    StringBuilder xml = FileExt.getFileContent("src/test/resources/contacts.xml");
    XStream xStream = new XStream();
    xStream.processAnnotations(ContactData.class);
    List<ContactData> contacts = (List<ContactData>)xStream.fromXML(xml.toString());
    return contacts.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
  }

  @DataProvider
  public Iterator<Object[]> validContactsFromJson() throws IOException {
    StringBuilder json = FileExt.getFileContent("src/test/resources/contacts.json");
    Gson gson = new Gson();
    List<ContactData> contacts = gson.fromJson(json.toString(), new TypeToken<List<ContactData>>(){}.getType());
    return contacts.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
  }

  @Test (dataProvider = "validContactsFromXML")
  public void testContactCreation(ContactData contact) throws Exception {
    app.goTo().baseURL();
    contact.withPhoto(new File("src/test/resources/images.jpg")).inGroups(app.db().groups().any());
    Contacts before = app.contact().all();
    app.goTo().newContactPage();
    app.contact().create(contact);
    assertThat("Test contacts quantity.", app.contact().count(), equalTo(before.size() + 1));
    Contacts after = app.contact().all();
    assertThat("Test contacts content.", after, equalTo(before.withAdded(contact.withId(after.maxId()))));
  }
}
