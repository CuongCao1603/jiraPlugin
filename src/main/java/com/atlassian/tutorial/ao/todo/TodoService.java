package com.atlassian.tutorial.ao.todo;

import com.atlassian.activeobjects.tx.Transactional;

import java.util.List;

@Transactional
public interface TodoService
{
    Todo add(String description);

    List<TodoDTO> all();

    void delete(long todoId);

    void update(long todoId, String newDescription);

    List<Todo> searchByDescription(String searchQuery);
}
