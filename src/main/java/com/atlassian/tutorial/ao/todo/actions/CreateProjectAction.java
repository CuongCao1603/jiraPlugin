package com.atlassian.tutorial.ao.todo.actions;

import com.atlassian.jira.bc.project.ProjectService;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.web.action.JiraWebActionSupport;

public class CreateProjectAction extends JiraWebActionSupport {
    private final ProjectService projectService;

    // Constructor với dependency injection
    public CreateProjectAction(ProjectService projectService) {
        this.projectService = projectService;
    }

    // Phương thức để tạo dự án
    public String doCreateProject() {
        ApplicationUser user = getLoggedInUser();
//        ProjectService.CreateProjectValidationResult validationResult =
//                projectService.validateCreateProject(user,
//                        "New Project Key",
//                        "New Project Name",
//                        "Description",
//                        "Project Lead's Username",
//                        null,  // templateKey, sử dụng null để sử dụng mẫu mặc định
//                        null); // url
//
//        if (validationResult.isValid()) {
//            projectService.createProject(validationResult);
            return "success";
//        } else {
//            addErrorCollection(validationResult.getErrorCollection());
//            return "error";
//        }
    }
}

