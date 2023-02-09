package ru.stqa.pft.rest.appmanager;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Header;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import ru.stqa.pft.rest.model.Issue;

public class RestAssuredHelper {
    private final ApplicationManager app;

    public RestAssuredHelper(ApplicationManager app) {
        this.app = app;
    }

    public Issue createIssue(Issue issue) {
        String address = app.getProperty("web.address");
        Issue createdIssue = RestAssured
                .given()
                .header(getAuthHeader())
                .contentType(ContentType.JSON)
                .body(issue)
                .post(address + "/rest/api/3/issue").as(Issue.class);
        return getIssue(createdIssue.getId());
    }

    public Issue getIssue(int issueId) {
        String address = app.getProperty("web.address");
        return RestAssured
                .given()
                .header(getAuthHeader())
                .get(address + "/rest/api/3/issue/" + issueId).as(Issue.class);
    }

    public Issue getIssue(String key) {
        String address = app.getProperty("web.address");
        return RestAssured
                .given()
                .header(getAuthHeader())
                .get(address + "/rest/api/3/issue/" + key).as(Issue.class);
    }

    private Header getAuthHeader() {
        String login = app.getProperty("web.adminLogin");
        String password = app.getProperty("web.adminPassword");
        String auth = Base64.encode((login + ":" + password).getBytes());
        String headerAuthorization = "Authorization";
        String headerAuthorizationValue = "Basic " + auth;
        return new Header(headerAuthorization, headerAuthorizationValue);
    }
}
