package com.atlassian.tutorial.ao.todo.actions;

import com.atlassian.jira.security.request.RequestMethod;
import com.atlassian.jira.security.request.SupportedMethods;
import com.atlassian.jira.web.action.JiraWebActionSupport;
import com.atlassian.tutorial.ao.todo.dto.TodoDto;
import com.atlassian.tutorial.ao.todo.service.TodoService;

import java.util.List;

@SupportedMethods({RequestMethod.POST, RequestMethod.PUT, RequestMethod.GET})
public class UpdateTodoAction extends JiraWebActionSupport{

    private final TodoService todoService;
    public List<TodoDto> todolist;


    public UpdateTodoAction(TodoService todoService){
        this.todoService = todoService;
    }

    @Override
    public String execute() throws Exception {
        try {
            // Lấy thông tin từ request parameter
            int todoId = Integer.parseInt(getHttpRequest().getParameter("id"));
            int userId = Integer.parseInt(getHttpRequest().getParameter("user_id"));
            String description = getHttpRequest().getParameter("description");

            String summary = getHttpRequest().getParameter("summary");
            Boolean isComplete = getHttpRequest().getParameter("complete").isEmpty();

//        boolean complete = Boolean.parseBoolean(getHttpRequest().getParameter("complete"));
            // Cập nhật Todo trong todoService
            todoService.updateTodo(todoId,userId,description,  summary, isComplete);
            setTodolist(todoService.getAllTodos());
            return "success";
        } catch (Exception e) {
            // exception
            return "error";
        }
    }


//    public String doUpdate() {
//        // Lấy thông tin từ request parameter
//        long todoId = Long.parseLong(getHttpRequest().getParameter("todoId"));
//        String description = getHttpRequest().getParameter("description");
////        boolean complete = Boolean.parseBoolean(getHttpRequest().getParameter("complete"));
//
//        // Lấy Todo cần cập nhật từ todoService
////        Todo todoToUpdate = null;
////
////        // Cập nhật thông tin của Todo
////        todoToUpdate.setDescription(description);
////        todoToUpdate.setComplete(false);
//
//        // Cập nhật Todo trong todoService
//        todoService.update(todoId,description);
//        setTodolist(todoService.allDTO());
//        return "success";
//    }

    public List<TodoDto> getTodos() {
        return todolist;
    }
    public void setTodolist(List<TodoDto> todolist) {
        this.todolist = todolist;
    }
}
