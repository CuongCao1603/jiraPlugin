package com.atlassian.tutorial.ao.todo.actions;

import com.atlassian.jira.security.request.RequestMethod;
import com.atlassian.jira.security.request.SupportedMethods;
import com.atlassian.jira.web.action.JiraWebActionSupport;
import com.atlassian.tutorial.ao.todo.dto.TodoDto;
import com.atlassian.tutorial.ao.todo.service.TodoService;

import java.util.List;

@SupportedMethods({RequestMethod.POST, RequestMethod.PUT, RequestMethod.GET, RequestMethod.DELETE})
public class DeleteTodoAction extends JiraWebActionSupport{

    private final TodoService todoService;
    public List<TodoDto> todolist;


    public DeleteTodoAction(TodoService todoService){
        this.todoService = todoService;
    }

    @Override
    public String execute() throws Exception {
        try {
            // Lấy thông tin từ request parameter
            int todoId = Integer.parseInt(getHttpRequest().getParameter("id"));

//            // Xóa Todo từ todoService
//            todoService.deleteTodo(todoId);
//            setTodolist(todoService.getAllTodos());
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
