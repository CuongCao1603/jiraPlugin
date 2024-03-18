package com.atlassian.tutorial.ao.todo.model;

import net.java.ao.Entity;
import net.java.ao.Preload;
import net.java.ao.schema.StringLength;
import net.java.ao.schema.Table;

import java.util.Date;
import java.util.List;

@Table("Issue_Test")
@Preload
public interface Issue extends Entity {
    // Set and get ID of the project
    void setProjectId(String projectId);
    String getProjectId();

    // Set and get summary of the issue
    void setSummary(String summary);
    String getSummary();

    // Set and get issue type ID
    void setIssueTypeId(String issueTypeId);
    String getIssueTypeId();

    // Labels can be complex to model directly; consider storing as JSON or as a separate table
    void setLabels(List<String> labels);
    @StringLength(StringLength.UNLIMITED)
    List<String> getLabels();

    // Set and get original estimate time
    void setOriginalEstimate(String originalEstimate);
    String getOriginalEstimate();

    // Set and get remaining estimate time
    void setRemainingEstimate(String remainingEstimate);
    String getRemainingEstimate();

    // Set and get description of the issue
    void setDescription(String description);
    String getDescription();

    // Set and get due date
    // Set and get due date as Date
    void setDueDate(Date dueDate);
    Date getDueDate();
}

