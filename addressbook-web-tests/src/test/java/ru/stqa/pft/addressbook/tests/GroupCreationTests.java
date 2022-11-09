package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation() {
    app.goTo().groupPage();
    Groups before = app.group().getAll();
    GroupData group = new GroupData()
            .withName("Test 1")
            .withHeader("Test 1")
            .withFooter("Test 1");
    app.group().create(group);
    assertThat("Test groups quantity.", app.group().count(), equalTo(before.size() + 1));
    Groups after = app.group().getAll();
    assertThat("Test groups content.", after, equalTo(before.withAdded(group.withId(after.getMaxId()))));
  }

  @Test
  public void testBadGroupCreation() {
    app.goTo().groupPage();
    Groups before = app.group().getAll();
    GroupData group = new GroupData()
            .withName("Test 1'")
            .withHeader("Test 1")
            .withFooter("Test 1");
    app.group().create(group);
    assertThat("Test groups quantity.", app.group().count(), equalTo(before.size()));
    Groups after = app.group().getAll();
    assertThat("Test groups content.", after, equalTo(before));
  }
}
