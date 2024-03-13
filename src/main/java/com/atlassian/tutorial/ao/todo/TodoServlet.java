package com.atlassian.tutorial.ao.todo;

import com.atlassian.jira.bc.issue.IssueService;
import com.atlassian.jira.bc.issue.search.SearchService;
import com.atlassian.jira.bc.project.ProjectService;
import com.atlassian.jira.config.ConstantsManager;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.templaterenderer.TemplateRenderer;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.plugin.spring.scanner.annotation.imports.JiraImport;
import com.atlassian.sal.api.user.UserManager;
import com.atlassian.tutorial.ao.todo.dto.TodoDto;
import com.atlassian.tutorial.ao.todo.service.TodoService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static com.google.common.base.Preconditions.*;

@Scanned
public final class TodoServlet extends HttpServlet
{
    private static final String LIST_ISSUES_TEMPLATE = "/templates/list.vm";
    private static final String NEW_ISSUE_TEMPLATE2 = "/templates/new2.vm";
    private static final String NEW_ISSUE_TEMPLATE = "/templates/new.vm";
    private static final String EDIT_ISSUE_TEMPLATE = "/templates/edit.vm";
    private final TodoService todoService;
    @ComponentImport
    private final UserManager userManager;

//    @Inject
//    public TodoServlet(TodoService todoService, UserManager userManager)
//    {
//        this.todoService = checkNotNull(todoService);
//        this.userManager = checkNotNull(userManager);
//    }

    @Inject
    public TodoServlet(TodoService todoService, UserManager userManager, JiraAuthenticationContext authenticationContext)
    {
        this.todoService = checkNotNull(todoService);
        this.userManager = checkNotNull(userManager);
        this.authenticationContext = checkNotNull(authenticationContext);
    }

//    private static final Logger log = LoggerFactory.getLogger(TodoServlet.class);

    @JiraImport
    private IssueService issueService;
    @JiraImport
    private ProjectService projectService;
    @JiraImport
    private SearchService searchService;
    @JiraImport
    private TemplateRenderer templateRenderer;
    @JiraImport
    private JiraAuthenticationContext authenticationContext;
    @JiraImport
    private ConstantsManager constantsManager;

    private String successMessage;
    private String errorMessage;
    private String notFoundMessage;


//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//        if (!enforceLoggedIn(req, res)) {
//            return;
//        }
//        List<TodoDto> todos = todoService.all();
//        req.setAttribute("todos", todos);
////        RequestDispatcher dispatcher = req.getRequestDispatcher("/atlassian/tutorial/ao/todo/new2.jsp");
////
////        dispatcher.forward(req, res);
//
//
////        Map<String, Object> context = new HashMap<>();
////        //        String searchQuery = req.getParameter("search");
////        List<Todo> todos =todoService.all();
//////                (null != searchQuery && !searchQuery.isEmpty()) ?
//////                        todoService.searchByDescription(searchQuery) :
//////                        todoService.all();
//////        List<Todo> searchResults = todoService.searchByDescription(searchQuery);
////        context.put("todos", todos);
////////        context.put("searchResults", searchResults);
////////        context.put("successMessage", successMessage);
////////        context.put("errorMessage", errorMessage);
////        templateRenderer.render(NEW_ISSUE_TEMPLATE2,context,res.getWriter());
//
//    }
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
//    {
//        if (!enforceLoggedIn(req, res))
//        {
//            return;
//        }
//
//        final String description = req.getParameter("task");
//        if (req.getParameter("submit").equals("Add")) {
//            try {
//                if (description.isEmpty()) {
//                    errorMessage = "Description cannot be empty.";
//                    return;
//                } else {
//                    todoService.add(description);
//                    successMessage = "Todo added successfully.";
//                }
//            } catch (Exception e) {
//                errorMessage = "Failed to add todo.";
//            }
//        } else if (req.getParameter("submit").equals("Update")) {
//            try {
//                long todoId = Long.parseLong(req.getParameter("id"));
//                if (description.isEmpty()) {
//                    errorMessage = "Description cannot be empty.";
//                    return;
//                } else {
//                    todoService.update(todoId, description);
//                    successMessage = "Todo updated successfully.";
//                }
//            } catch (Exception e) {
//                errorMessage = "Failed to update todo.";
//            }
//        } else if (req.getParameter("submit").equals("Delete")) {
//            try {
//                long todoId = Long.parseLong(req.getParameter("id"));
//                todoService.delete(todoId);
//                successMessage = "Todo deleted successfully.";
//            } catch (Exception e) {
//                errorMessage = "Failed to delete todo.";
//            }
//        }
//
//        String redirectUrl = req.getContextPath() + "/plugins/servlet/todo/list";
//
//        res.sendRedirect(redirectUrl);
//        req.getRequestDispatcher(redirectUrl).forward(req, res);
//    }

    private boolean enforceLoggedIn(HttpServletRequest req, HttpServletResponse res) throws IOException
    {
        if (userManager.getRemoteUser() == null)
        {
            res.sendRedirect(req.getContextPath() + "/plugins/servlet/login");
            return false;
        }
        return true;
    }
}
