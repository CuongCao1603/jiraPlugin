package com.atlassian.tutorial.ao.todo.service;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.activeobjects.tx.Transactional;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.tutorial.ao.todo.dto.TodoDto;
import com.atlassian.tutorial.ao.todo.model.Todo;

import java.util.List;

@Scanned
@Transactional
public interface TodoService {
    Todo createTodo(
            int userId,
//            String user,
            String summary, String description, boolean isComplete);

    Todo getTodoById(int id);

    Boolean updateTodo(int id,
                       int userId,
                       String summary, String description, boolean isComplete);

    boolean deleteTodo(int id);

    List<TodoDto> getAllTodos();

    List<Todo> searchByDescription(String searchQuery);


    List<Todo> searchTodosByUsername(String username);

    List<TodoDto> getAllTodosPaged(int pageNumber, int pageSize);

    List<TodoDto> searchTodosBySummary(String summary);
}
