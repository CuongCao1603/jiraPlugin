package com.atlassian.tutorial.ao.todo.rest;


import com.atlassian.jira.bc.issue.IssueService;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.IssueInputParameters;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugins.rest.common.security.AnonymousAllowed;
import com.atlassian.tutorial.ao.todo.dto.IssueDto;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.PrintWriter;
import java.io.StringWriter;

@Path("/issuess")
@Scanned
public class IssueResource {
    @POST
    @AnonymousAllowed
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createIssue(IssueDto issueModel) {

        try {

            IssueService issueService = ComponentAccessor.getIssueService();
            ApplicationUser currentUser = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser();

            // Tạo tham số nhập cho vấn đề mới
            IssueInputParameters issueInputParameters = issueService.newIssueInputParameters();
            issueInputParameters.setProjectId(Long.parseLong(issueModel.getProjectId()))
                    .setSummary(issueModel.getSummary())
                    .setIssueTypeId(issueModel.getIssueTypeId())
                    .setDueDate(String.valueOf(issueModel.getDueDate()))
                    .setDescription(issueModel.getDescription());

            // Labels và Time Tracking có thể cần xử lý đặc biệt
            // Ví dụ: issueInputParameters.addLabel("bugfix");

            IssueService.CreateValidationResult validationResult = issueService.validateCreate(currentUser, issueInputParameters);

            if (validationResult.getErrorCollection().hasAnyErrors()) {
                // Xử lý lỗi ở đây
                return Response.status(Response.Status.BAD_REQUEST).entity(validationResult.getErrorCollection()).build();
            } else {
                IssueService.IssueResult createResult = issueService.create(currentUser, validationResult);
                if (createResult.isValid()) {
                    return Response.ok(createResult.getIssue()).build();
                } else {
                    // Xử lý lỗi tạo vấn đề
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(createResult.getErrorCollection()).build();
                }
            }
        } catch (Exception ex) {
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            String errorsStr = errors.toString();
            return Response.ok(errorsStr).build();
//            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
//                    .entity(ex.getMessage()).build();
        }
    }
}
