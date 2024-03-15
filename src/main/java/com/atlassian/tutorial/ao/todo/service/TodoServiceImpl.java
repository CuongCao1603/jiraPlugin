package com.atlassian.tutorial.ao.todo.service;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.sal.api.user.UserManager;
import com.atlassian.tutorial.ao.todo.dto.TodoDto;
import com.atlassian.tutorial.ao.todo.model.Todo;
import com.atlassian.tutorial.ao.todo.model.User;
import com.google.common.collect.ImmutableMap;
import net.java.ao.Query;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    private final UserService userService;

    @Inject
    public TodoServiceImpl(ActiveObjects ao, UserManager userManager
            , UserService userService
    ) {
        this.ao = checkNotNull(ao);
        this.userManager = checkNotNull(userManager);
        this.userService = userService;
    }

    @Override
    public List<TodoDto> getAllTodos() {
        List<Todo> todos = ao.executeInTransaction(() ->
                Arrays.asList(ao.find(Todo.class)));

        return todos.stream()
                .map(todo -> new TodoDto(todo)) // Giả sử bạn có constructor phù hợp trong TodoDto
                .collect(Collectors.toList());
    }

    @Override
    public Todo createTodo(
            int userId,
//            String username,
            String summary, String description, boolean isComplete) {
        User user = userService.findUserById(userId);
        int getUserId = user.getID();
        return ao.executeInTransaction(() -> {
            Todo todo = ao.create(Todo.class);
            todo.setUserId(getUserId);
//            todo.setUser(username);
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
                              int userId,
                              String summary, String description, boolean isComplete) {
        return ao.executeInTransaction(() -> {
            Todo todo = ao.get(Todo.class, id);
            if (todo != null) {
                // Giả sử chúng ta đã sửa đổi model Todo để thuộc tính user là kiểu String
                todo.setUserId(userId); // Set lại người dùng dựa vào username dạng String
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

    @Override
    public List<Todo> searchTodosByUsername(String username) {
        User user = findUserByUsername(username);
        if (user != null) {
            return Arrays.asList(ao.find(Todo.class, Query.select().where("USER_ID = ?", user.getID())));
        }
        return new ArrayList<>();
    }

    @Override
    public List<TodoDto> getAllTodosPaged(int pageNumber, int pageSize) {
        // Tính toán offset dựa trên số trang và kích thước trang
        int offset = (pageNumber - 1) * pageSize;
        List<Todo> todos = Arrays.asList(ao.find(Todo.class, Query.select().limit(pageSize).offset(offset)));
        return todos.stream()
                .map(todo -> new TodoDto(todo)) // Giả sử bạn có constructor phù hợp trong TodoDto
                .collect(Collectors.toList());
    }

    @Override
    public List<TodoDto> searchTodosBySummary(String summary) {
        // Use Active Objects to find Todo items where the summary contains the specified text.
        // The '%' symbols are used for SQL LIKE wildcard searches, allowing for any characters to be present before and after the specified summary text.
        List<Todo> todos = Arrays.asList(ao.find(Todo.class, Query.select().where("SUMMARY LIKE ?", "%" + summary + "%")));
        return todos.stream()
                .map(todo -> new TodoDto(todo))
                .collect(Collectors.toList());
    }


    private User findUserByUsername(String username) {
        User[] users = ao.find(User.class, Query.select().where("NAME LIKE ?", "%" + username + "%"));
        if (users.length > 0) {
            return users[0]; // Giả sử username là duy nhất và chỉ trả về kết quả đầu tiên
        }
        return null;
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

}
