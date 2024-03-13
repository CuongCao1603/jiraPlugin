package com.atlassian.tutorial.ao.todo.service;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.sal.api.user.UserManager;
import com.atlassian.tutorial.ao.todo.model.Todo;
import com.atlassian.tutorial.ao.todo.model.User;
import com.google.common.collect.ImmutableMap;
import net.java.ao.Query;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;


//@Service
@Named
public class TodoServiceImpl implements TodoService {
    @ComponentImport
    private final ActiveObjects ao;
    @ComponentImport
    private final UserManager userManager;

    @Inject
    public TodoServiceImpl(ActiveObjects ao, UserManager userManager) {
        this.ao = checkNotNull(ao);
        this.userManager = checkNotNull(userManager);
    }

    @Override
    public List<Todo> getAllTodos() {
        return ao.executeInTransaction(() ->
                Arrays.asList(ao.find(Todo.class)));

    }

//    @Override
//    public Todo add(String description)
//    {
//        User user = getOrCreateUser(ao, currentUserName());
//        final Todo todo = ao.create(Todo.class, new DBParam("USER_ID", user.getID()));
//        todo.setDescription(description);
//        todo.setComplete(false);
//        todo.save();
//        return todo;
//    }
//
//    @Override
//    public List<TodoDto> all()
//    {
//        User user = getOrCreateUser(ao, currentUserName());
//        List<Todo> todoList =newArrayList(ao.find(Todo.class, Query.select().where("USER_ID = ?", user.getID())));
//        List<TodoDto> todoDTOList = new ArrayList<>();
//
//        for (Todo todo : todoList) {
//            TodoDto todoDTO = new TodoDto();
//            todoDTO.setId(todo.getID());
//            todoDTO.setDescription(todo.getDescription());
//            todoDTO.setComplete(todo.isComplete());
//            // Thiết lập các thuộc tính khác của TodoDTO (nếu có)
//            todoDTOList.add(todoDTO);
//        }
//
//        return todoDTOList;
//    }
//
//    @Override
//    public void delete(long todoId) {
//        Todo[] todos = ao.find(Todo.class, Query.select().where("ID = ?", todoId));
//        Todo todo = todos.length > 0 ? todos[0] : null;
//        if (todo != null) {
//            ao.delete(todo);
//        }
//    }
//
//    @Override
//    public void update(long todoId, String newDescription) {
//        Todo[] todos = ao.find(Todo.class, Query.select().where("ID = ?", todoId));
//        Todo todo = todos.length > 0 ? todos[0] : null;
//        if (todo != null) {
//            todo.setDescription(newDescription);
//            todo.save();
//        }
//    }

    @Override
    public Todo createTodo(
            String username,
            String summary, String description, boolean isComplete) {
        return ao.executeInTransaction(() -> {
            Todo todo = ao.create(Todo.class);
          todo.setUser(username);
            todo.setSummary(summary);
            todo.setDescription(description);
            todo.setComplete(isComplete);
            todo.save();
            return todo;
        });
    }

    @Override
    public Todo getTodoById(int id) {
        return ao.executeInTransaction(() -> ao.get(Todo.class, id));
    }

    @Override
    public Boolean updateTodo(int id,
                              String username,
                              String summary, String description, boolean isComplete) {
        return ao.executeInTransaction(() -> {
            Todo todo = ao.get(Todo.class, id);
            if (todo != null) {
                // Giả sử chúng ta đã sửa đổi model Todo để thuộc tính user là kiểu String
//                todo.setUser(username); // Set lại người dùng dựa vào username dạng String
                todo.setSummary(summary);
                todo.setDescription(description);
                todo.setComplete(isComplete);
                todo.save();
                return true;
            }
            return false;
        });
    }


    @Override
    public boolean deleteTodo(int id) {
        return ao.executeInTransaction(() -> {
            try {
                ao.delete(ao.get(Todo.class, id));
                return true;
            } catch (Exception e) {
                return false;
            }
        });
    }


    @Override
    public List<Todo> searchByDescription(String searchQuery) {
        User user = getOrCreateUser(ao, currentUserName());
        String query = "USER_ID = ? AND DESCRIPTION LIKE ?";
        String searchParam = "%" + searchQuery + "%";
        return newArrayList(ao.find(Todo.class, Query.select().where(query, user.getID(), searchParam)));
    }


    private String currentUserName() {
        return userManager.getRemoteUser().getUsername();
    }

    private User getOrCreateUser(ActiveObjects ao, String userName) {
        User[] users = ao.find(User.class, Query.select().where("NAME = ?", userName));
        if (users.length == 0) {
            return createUser(ao, userName);
        } else if (users.length == 1) {
            return users[0];
        } else {
            throw new IllegalStateException("There shouldn't be 2 users with the same username! " + userName);
        }
    }

    private User createUser(ActiveObjects ao, String userName) {
        return ao.create(User.class, ImmutableMap.<String, Object>of("NAME", userName));
    }

//    protected void doGett(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//        if (!enforceLoggedIn(req, res)) {
//            return;
//        }
//
//        final PrintWriter w = res.getWriter();
//        w.printf("<h1>Todos (%s)</h1>", userManager.getRemoteUser().getUsername());
//
//        // Form to add new TODOs
//        w.write("<form method=\"post\">");
//        w.write("<input type=\"text\" name=\"task\" size=\"25\" placeholder=\"Enter new task\"/>");
//        w.write("&nbsp;&nbsp;");
//        // Add input for User field
//        w.write("<input type=\"text\" name=\"user\" size=\"25\" placeholder=\"Assign to\"/>");
//        // Dropdown menu for status
//        w.write("<select name=\"status\">");
//        w.write("<option value=\"complete\">Complete</option>");
//        w.write("<option value=\"submit\">Submit</option>");
//        w.write("<option value=\"draft\">Draft</option>");
//        w.write("</select>");
//        w.write("&nbsp;&nbsp;");
//        w.write("<input type=\"submit\" name=\"submit\" value=\"Add\"/>");
//        w.write("</form>");
//
//        w.write("<ol>");
//
//        for (Todo todo : todoService.all()) {
//            w.print("<li>");
//            w.printf("<strong>Description:</strong> %s <br>", todo.getDescription());
//            w.printf("<strong>User:</strong> %s <br>", todo.getUser().getName());
//            w.printf("<strong>Complete:</strong> %s <br>", todo.isComplete() ? "Yes" : "No");
//            w.print("</li>");
//        }
//
//        w.write("</ol>");
//        w.write("<script language='javascript'>document.forms[0].elements[0].focus();</script>");
//
//        w.close();
//    }
//
//    protected void doPostt(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
//    {
//        if (!enforceLoggedIn(req, res))
//        {
//            return;
//        }
//
//        final String description = req.getParameter("task");
//        final String user = req.getParameter("user");
//        final String status = (req.getParameter("complete"));
//
//        todoService.add(description, user);
//        res.sendRedirect(req.getContextPath() + "/plugins/servlet/todo/list");
//    }

}
