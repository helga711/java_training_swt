package ru.stqa.pft.rest.model;

import java.util.Objects;

public class IssueFields {
    private String summary;
    private IssueType issuetype;
    private Project project;

    private Status status;

    public String getSummary() {
        return summary;
    }

    public IssueFields withSummary(String summary) {
        this.summary = summary;
        return this;
    }

    public IssueType getIssuetype() {
        return issuetype;
    }

    public IssueFields withIssuetype(IssueType issuetype) {
        this.issuetype = issuetype;
        return this;
    }

    public Project getProject() {
        return project;
    }

    public IssueFields withProject(Project project) {
        this.project = project;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public IssueFields withStatus(Status status) {
        this.status = status;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IssueFields that = (IssueFields) o;
        return Objects.equals(summary, that.summary) && Objects.equals(issuetype, that.issuetype) && Objects.equals(project, that.project);
    }

    @Override
    public int hashCode() {
        return Objects.hash(summary, issuetype, project);
    }

    @Override
    public String toString() {
        return "IssueFields{" +
                "summary='" + summary + '\'' +
                ", issuetype=" + issuetype +
                ", project=" + project +
                '}';
    }
}
