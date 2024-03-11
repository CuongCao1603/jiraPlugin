package com.atlassian.tutorial.ao.todo;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.sal.api.user.UserManager;
import com.google.common.collect.ImmutableMap;
import net.java.ao.DBParam;
import net.java.ao.Query;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;

@Scanned
@Named
public class TodoServiceImpl implements TodoService
{
    @ComponentImport
    private final ActiveObjects ao;
    @ComponentImport
    private final UserManager userManager;

    @Inject
    public TodoServiceImpl(ActiveObjects ao, UserManager userManager)
    {
        this.ao = checkNotNull(ao);
        this.userManager = checkNotNull(userManager);
    }

    @Override
    public Todo add(String description, String summary)
    {
        User user = getOrCreateUser(ao, currentUserName());
        final Todo todo = ao.create(Todo.class, new DBParam("USER_ID", user.getID()));
        todo.setDescription(description);
//        todo.setComplete(status.equalsIgnoreCase("true")? true : false);
//        todo.setUser(username);
        todo.save();
        return todo;
    }

    @Override
    public List<Todo> all()
    {
        User user = getOrCreateUser(ao, currentUserName());
        return newArrayList(ao.find(Todo.class, Query.select().where("USER_ID = ?", user.getID())));
    }

    private String currentUserName() {
        return userManager.getRemoteUser().getUsername();
    }

    private User getOrCreateUser(ActiveObjects ao, String userName)
    {
        User[] users = ao.find(User.class, Query.select().where("NAME = ?", userName));
        if (users.length == 0) {
            return createUser(ao, userName);
        } else if (users.length == 1) {
            return users[0];
        } else {
            throw new IllegalStateException("There shouldn't be 2 users with the same username! " + userName);
        }
    }

    private User createUser(ActiveObjects ao, String userName)
    {
        return ao.create(User.class, ImmutableMap.<String, Object>of("NAME", userName));
    }
    protected void doGett(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        if (!enforceLoggedIn(req, res)) {
            return;
        }

        final PrintWriter w = res.getWriter();
        w.printf("<h1>Todos (%s)</h1>", userManager.getRemoteUser().getUsername());

        // Form to add new TODOs
        w.write("<form method=\"post\">");
        w.write("<input type=\"text\" name=\"task\" size=\"25\" placeholder=\"Enter new task\"/>");
        w.write("&nbsp;&nbsp;");
        // Add input for User field
        w.write("<input type=\"text\" name=\"user\" size=\"25\" placeholder=\"Assign to\"/>");
        // Dropdown menu for status
        w.write("<select name=\"status\">");
        w.write("<option value=\"complete\">Complete</option>");
        w.write("<option value=\"submit\">Submit</option>");
        w.write("<option value=\"draft\">Draft</option>");
        w.write("</select>");
        w.write("&nbsp;&nbsp;");
        w.write("<input type=\"submit\" name=\"submit\" value=\"Add\"/>");
        w.write("</form>");

        w.write("<ol>");

        for (Todo todo : todoService.all()) {
            w.print("<li>");
            w.printf("<strong>Description:</strong> %s <br>", todo.getDescription());
            w.printf("<strong>User:</strong> %s <br>", todo.getUser().getName());
            w.printf("<strong>Complete:</strong> %s <br>", todo.isComplete() ? "Yes" : "No");
            w.print("</li>");
        }

        w.write("</ol>");
        w.write("<script language='javascript'>document.forms[0].elements[0].focus();</script>");

        w.close();
    }

    protected void doPostt(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        if (!enforceLoggedIn(req, res))
        {
            return;
        }

        final String description = req.getParameter("task");
        final String user = req.getParameter("user");
        final String status = (req.getParameter("complete"));

        todoService.add(description, user);
        res.sendRedirect(req.getContextPath() + "/plugins/servlet/todo/list");
    }

}
