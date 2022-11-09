package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().groupPage();
    if (app.group().getAll().size() == 0) {
      app.group().create(new GroupData().withName("Test 1"));
    }
  }

  @Test
  public void testGroupDeletion() {
    Groups before = app.group().getAll();
    GroupData deletedGroup = before.getSome();
    app.group().delete(deletedGroup);
    assertThat("Test groups quantity.", app.group().count(), equalTo(before.size() - 1));
    Groups after = app.group().getAll();
    assertThat("Test groups content.", after, equalTo(before.without(deletedGroup)));
  }
}
