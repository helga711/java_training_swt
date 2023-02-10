package ru.stqa.pft.rest.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.rest.model.Issue;
import ru.stqa.pft.rest.model.IssueFields;
import ru.stqa.pft.rest.model.IssueType;
import ru.stqa.pft.rest.model.Project;

import static org.testng.Assert.assertEquals;

public class RestAssuredTests extends TestBase{

    @Test
    public void testCreateIssue() {
        Issue issue = new Issue()
                .withFields(new IssueFields()
                        .withSummary("Test issue")
                        .withIssuetype(new IssueType().withId(10103))
                        .withProject(new Project().withId(10302)));
        Issue createdIssue = app.restAssured().createIssue(issue);
        assertEquals(createdIssue, issue.withId(createdIssue.getId()).withKey(createdIssue.getKey()));
    }
}
