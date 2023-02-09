package ru.stqa.pft.rest.tests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import org.apache.http.Header;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicHeader;
import org.testng.annotations.Test;
import ru.stqa.pft.rest.model.Issue;
import ru.stqa.pft.rest.model.IssueFields;
import ru.stqa.pft.rest.model.IssueType;
import ru.stqa.pft.rest.model.Project;

import java.io.IOException;

import static org.testng.Assert.*;

public class RestTests extends TestBase{

    @Test
    public void testCreateIssue() throws IOException {
        Issue issue = new Issue()
                .withFields(new IssueFields()
                        .withSummary("Test issue")
                        .withIssuetype(new IssueType().withId(10103))
                        .withProject(new Project().withId(10302)));
        Issue createdIssue = app.rest().createIssue(issue);
        assertEquals(createdIssue, issue.withId(createdIssue.getId()).withKey(createdIssue.getKey()));
    }
}
