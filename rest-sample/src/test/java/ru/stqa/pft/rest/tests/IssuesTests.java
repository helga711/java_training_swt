package ru.stqa.pft.rest.tests;

import org.testng.annotations.Test;

public class IssuesTests extends TestBase{

    @Test
    public void testIssue() {
        skipIfNotFixed("TEST-18821");
    }
}
