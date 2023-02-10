package ru.stqa.pft.rest.appmanager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.http.Header;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicHeader;
import ru.stqa.pft.rest.model.Issue;

import java.io.IOException;

public class RestHelper {
    private final ApplicationManager app;

    public RestHelper(ApplicationManager app) {
        this.app = app;
    }

    public Issue createIssue(Issue issue) throws IOException {
        String address = app.getProperty("web.address");
        Gson gson = new GsonBuilder().create();
        String jsonPost = Request.Post(address + "/rest/api/3/issue")
                .addHeader(getAuthHeader())
                .bodyString(gson.toJson(issue), ContentType.APPLICATION_JSON)
                .execute().returnContent().asString();
        Issue createdIssue = new Gson().fromJson(jsonPost, Issue.class);

        return getIssue(createdIssue.getId());
    }

    public Issue getIssue(int issueId) throws IOException {
        String address = app.getProperty("web.address");
        String jsonGet = Request.Get(address + "/rest/api/3/issue/" + issueId)
                .addHeader(getAuthHeader())
                .execute().returnContent().asString();
        return new Gson().fromJson(jsonGet, Issue.class);
    }

    public Issue getIssue(String key) throws IOException {
        String address = app.getProperty("web.address");
        String jsonGet = Request.Get(address + "/rest/api/3/issue/" + key)
                .addHeader(getAuthHeader())
                .execute().returnContent().asString();
        return new Gson().fromJson(jsonGet, Issue.class);
    }

    private Header getAuthHeader() {
        String login = app.getProperty("web.adminLogin");
        String password = app.getProperty("web.adminPassword");
        String auth = Base64.encode((login + ":" + password).getBytes());
        String headerAuthorization = "Authorization";
        String headerAuthorizationValue = "Basic " + auth;
        return new BasicHeader(headerAuthorization, headerAuthorizationValue);
    }
}
