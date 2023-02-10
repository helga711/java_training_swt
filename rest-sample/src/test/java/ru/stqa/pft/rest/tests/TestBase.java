package ru.stqa.pft.rest.tests;

import org.testng.SkipException;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.rest.appmanager.ApplicationManager;
import ru.stqa.pft.rest.model.Issue;

import java.io.IOException;

public class TestBase {

    protected static final ApplicationManager app = new ApplicationManager();

    @BeforeSuite
    public void init() throws IOException {
        app.init();
    }

    protected boolean isIssueOpen(String issueKey) {
        Issue issue = app.restAssured().getIssue(issueKey);
        return issue.getFields().getStatus().getId() < 5;
    }

    protected void skipIfNotFixed(String issueKey) {
        if (isIssueOpen(issueKey)) {
            System.out.println("Ignored because of issue " + issueKey);
            throw new SkipException("Ignored because of issue " + issueKey);
        }
    }
}
