package com.atlassian.tutorial.ao.todo.actions;
import com.atlassian.jira.web.action.JiraWebActionSupport;

import java.util.ArrayList;
import java.util.List;
import com.atlassian.jira.security.request.SupportedMethods;
import com.atlassian.jira.security.request.RequestMethod;
import com.atlassian.tutorial.ao.todo.dto.TodoDto;
import com.atlassian.tutorial.ao.todo.service.TodoService;

@SupportedMethods({RequestMethod.POST, RequestMethod.PUT, RequestMethod.GET})
public class TodoAction extends JiraWebActionSupport{

    private final TodoService todoService;
    public List<TodoDto> todolist;


    public TodoAction(TodoService todoService){
        this.todoService = todoService;
    }

    @Override
    public String execute() throws Exception {
        try {

            setTodolist(todoService.getAllTodos());
            return "success";
        } catch (Exception e) {
            // exception
            return "error";
        }
    }


    public List<TodoDto> getTodos() {
        return todolist;
    }
    public void setTodolist(List<TodoDto> todolist) {
        this.todolist = todolist;
    }
}
