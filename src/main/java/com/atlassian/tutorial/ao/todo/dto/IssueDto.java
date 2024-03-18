package com.atlassian.tutorial.ao.todo.dto;

import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.tutorial.ao.todo.model.Issue;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;
import java.util.List;

//@NoArgsConstructor
//@Getter
//@Setter
@Scanned
public class IssueDto {
    @JsonProperty("projectId")
    private String projectId;
    @JsonProperty("summary")
    private String summary;
    @JsonProperty("issueTypeId")
    private String issueTypeId;
    @JsonProperty("labels")
    private List<String> labels;
    //    private String originalEstimate;
//    private String remainingEstimate;
    @JsonProperty("description")
    private String description;
    @JsonProperty("dueDate")
    private Date dueDate;

    // Constructor với đầy đủ tham số
    public IssueDto(Issue issue) {
        this.projectId = projectId;
        this.summary = summary;
        this.issueTypeId = issueTypeId;
        this.labels = labels;
//        this.originalEstimate = originalEstimate;
//        this.remainingEstimate = remainingEstimate;
        this.description = description;
        this.dueDate = dueDate;
    }

    // Constructor mặc định
    public IssueDto() {
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getIssueTypeId() {
        return issueTypeId;
    }

    public void setIssueTypeId(String issueTypeId) {
        this.issueTypeId = issueTypeId;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

//    public String getOriginalEstimate() {
//        return originalEstimate;
//    }
//
//    public void setOriginalEstimate(String originalEstimate) {
//        this.originalEstimate = originalEstimate;
//    }
//
//    public String getRemainingEstimate() {
//        return remainingEstimate;
//    }
//
//    public void setRemainingEstimate(String remainingEstimate) {
//        this.remainingEstimate = remainingEstimate;
//    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}
