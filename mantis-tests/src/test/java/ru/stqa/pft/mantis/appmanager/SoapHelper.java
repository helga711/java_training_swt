package ru.stqa.pft.mantis.appmanager;

import biz.futureware.mantis.rpc.soap.client.*;
import ru.stqa.pft.mantis.model.Issue;
import ru.stqa.pft.mantis.model.Project;
import ru.stqa.pft.mantis.model.Status;

import javax.xml.rpc.ServiceException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class SoapHelper {
    private final ApplicationManager app;

    public SoapHelper(ApplicationManager app) {
        this.app = app;
    }

    public Set<Project> getProjects() throws MalformedURLException, RemoteException, ServiceException {
        MantisConnectPortType mc = getMantisConnect();
        String adminLogin = app.getProperty("web.adminLogin");
        String adminPassword = app.getProperty("web.adminPassword");
        ProjectData[] projects = mc.mc_projects_get_user_accessible(adminLogin, adminPassword);
        return Arrays.stream(projects).map((p) -> new Project()
                .withName(p.getName()).withId(p.getId()))
                .collect(Collectors.toSet());
    }

    public MantisConnectPortType getMantisConnect() throws ServiceException, MalformedURLException {
        String address = app.getProperty("web.address");
        return new MantisConnectLocator()
                .getMantisConnectPort(new URL(address + "/api/soap/mantisconnect.php"));
    }

    public Issue addIssue(Issue issue) throws MalformedURLException, ServiceException, RemoteException {
        MantisConnectPortType mc = getMantisConnect();
        String adminLogin = app.getProperty("web.adminLogin");
        String adminPassword = app.getProperty("web.adminPassword");
        String[] categories = mc.mc_project_get_categories(adminLogin, adminPassword, BigInteger.valueOf(issue.getProject().getId()));
        IssueData issueData = new IssueData();
        issueData.setSummary(issue.getSummary());
        issueData.setDescription(issue.getDescription());
        issueData.setProject(new ObjectRef(BigInteger.valueOf(issue.getProject().getId()), issue.getProject().getName()));
        issueData.setCategory(categories[0]);
        BigInteger issueId = mc.mc_issue_add(adminLogin, adminPassword, issueData);
        return getIssue(issueId);
    }

    public Issue getIssue(BigInteger issueId) throws RemoteException, MalformedURLException, ServiceException {
        MantisConnectPortType mc = getMantisConnect();
        String adminLogin = app.getProperty("web.adminLogin");
        String adminPassword = app.getProperty("web.adminPassword");
        IssueData issueData = mc.mc_issue_get(adminLogin, adminPassword, issueId);
        return new Issue().withId(issueId.intValue())
                .withDescription(issueData.getDescription())
                .withSummary(issueData.getSummary())
                .withProject(new Project().withId(issueData.getProject().getId())
                        .withName(issueData.getProject().getName()))
                .withStatus(new Status().withId(issueData.getStatus().getId())
                        .withName(issueData.getStatus().getName()));
    }
}
