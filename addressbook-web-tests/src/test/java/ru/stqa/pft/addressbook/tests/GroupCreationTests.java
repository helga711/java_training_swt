package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class GroupCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validGroupsFromXML() throws IOException {
    StringBuilder xml = new StringBuilder();
    try (BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/groups.xml"))) {
      String line = reader.readLine();
      while (line != null) {
        xml.append(line);
        line = reader.readLine();
      }
    }
    XStream xStream = new XStream();
    xStream.processAnnotations(GroupData.class);
    List<GroupData> groups = (List<GroupData>)xStream.fromXML(xml.toString());
    return groups.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
  }

  @DataProvider
  public Iterator<Object[]> validGroupsFromJson() throws IOException {
    StringBuilder json = new StringBuilder();
    try(BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/groups.json"))) {
      String line = reader.readLine();
      while (line != null) {
        json.append(line);
        line = reader.readLine();
      }
    }
    Gson gson = new Gson();
    List<GroupData> groups = gson.fromJson(json.toString(), new TypeToken<List<GroupData>>(){}.getType());
    return groups.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
  }

  @Test (dataProvider = "validGroupsFromJson")
  public void testGroupCreation(GroupData group) {
    app.goTo().groupPage();
    Groups before = app.group().all();
    app.group().create(group);
    assertThat("Test groups quantity.", app.group().count(), equalTo(before.size() + 1));
    Groups after = app.group().all();
    assertThat("Test groups content.", after, equalTo(before.withAdded(group.withId(after.maxId()))));
  }

  @Test
  public void testBadGroupCreation() {
    app.goTo().groupPage();
    Groups before = app.group().all();
    GroupData group = new GroupData()
            .withName("Test 1'")
            .withHeader("Test 1")
            .withFooter("Test 1");
    app.group().create(group);
    assertThat("Test groups quantity.", app.group().count(), equalTo(before.size()));
    Groups after = app.group().all();
    assertThat("Test groups content.", after, equalTo(before));
  }
}
