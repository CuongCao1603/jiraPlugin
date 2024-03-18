package com.atlassian.tutorial.ao.todo.actions;

import com.atlassian.jira.web.action.JiraWebActionSupport;

import com.atlassian.jira.security.request.SupportedMethods;
import com.atlassian.jira.security.request.RequestMethod;

@SupportedMethods({RequestMethod.POST, RequestMethod.PUT, RequestMethod.GET})
public class IssueAction extends JiraWebActionSupport {
//    private final IssueService issueService;
//    public List<IssueDto> issueDtoList;
//
//
//    public CreateIssueAction(IssueService issueService) {
//        this.issueService = issueService;
//    }

    @Override
    public String execute() throws Exception {
        try {
//            issueService.newIssueInputParameters();
//            setTodolist((List<IssueDto>) issueService.newIssueInputParameters());
            return "success";
        } catch (Exception e) {
            // exception
            return "error";
        }
    }


//    public List<IssueDto> getTodos() {
//        return issueDtoList;
//    }
//
//    public void setTodolist(List<IssueDto> todolist) {
//        this.issueDtoList = todolist;
//    }


}
