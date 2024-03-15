package com.atlassian.tutorial.ao.todo.actions;
import com.atlassian.jira.web.action.JiraWebActionSupport;

import java.util.List;

import com.atlassian.jira.security.request.SupportedMethods;
import com.atlassian.jira.security.request.RequestMethod;
import com.atlassian.templaterenderer.TemplateRenderer;
import com.atlassian.tutorial.ao.todo.dto.TodoDto;
import com.atlassian.tutorial.ao.todo.service.TodoService;


@SupportedMethods({RequestMethod.POST, RequestMethod.PUT, RequestMethod.GET})
public class GlobalAdminAction extends JiraWebActionSupport{

    // Khai báo TemplateRenderer
    private final TemplateRenderer templateRenderer;

//    private final TodoMapper todoMapper;
    private final TodoService todoService;
    public List<TodoDto> todolist;
//    public List<TodoDTO> todoDTOSMapper;

    // Inject TemplateRenderer qua constructor
    public GlobalAdminAction(TemplateRenderer templateRenderer, TodoService todoService) {
        this.templateRenderer = templateRenderer;
//        this.todoMapper = todoMapper;
        this.todoService = todoService;
    }

    @Override
    public String execute() throws Exception {
        try {
//            setTodolist(todoService.getAllTodos());
//            for (Todo t :
//                    todolist) {
//                todoDTOSMapper.add(TodoMapper.mapTodoToDTO(t));
//            }
//            Map<String, Object> context = new HashMap<>();
//            context.put("todos", todolist); // Đặt danh sách todos vào context với tên "todos"
//
//            // Lấy PrintWriter từ HttpServletResponse để render template
//            PrintWriter writer = getHttpResponse().getWriter();
//
//            // Đường dẫn tới template Velocity của bạn. Đảm bảo đường dẫn chính xác.
//            String templatePath = "/templates/new2.vm";

            // Render template với context
//            templateRenderer.render(templatePath, context, writer);

            return "success"; // Sử dụng NONE để ngăn chặn JIRA thực hiện navigation tới một trang khác
        } catch (Exception e) {
            // Xử lý ngoại lệ
            return "error";
        }
//        try {
//
//            setTodolist(todoService.all());
//            return "success";
//        } catch (Exception e) {
//            // exception
//            return "error";
//        }
    }


    public String doList() {
//        todolist = todoService.all();
        return "success";
    }

    public String doAdd() {
        // Lấy thông tin từ request parameter
        String description = getHttpRequest().getParameter("description");
//        boolean complete = Boolean.parseBoolean(getHttpRequest().getParameter("complete"));

        // Tạo một Todo mới và đặt thông tin
//        Todo newTodo = null;
//        newTodo.setDescription(description);
//        newTodo.setComplete(false);

//        // Thêm Todo mới vào todoService
//        if(null!=todoService.add(description)){
//            return "success";
//        }
        return "error";
    }

    public String doUpdate() {
        // Lấy thông tin từ request parameter
        long todoId = Long.parseLong(getHttpRequest().getParameter("todoId"));
        String description = getHttpRequest().getParameter("description");
//        boolean complete = Boolean.parseBoolean(getHttpRequest().getParameter("complete"));

        // Lấy Todo cần cập nhật từ todoService
//        Todo todoToUpdate = null;
//
//        // Cập nhật thông tin của Todo
//        todoToUpdate.setDescription(description);
//        todoToUpdate.setComplete(false);

        // Cập nhật Todo trong todoService
//        todoService.update(todoId,description);

        return "success";
    }

    public String doDelete() {
        // Lấy thông tin từ request parameter
        long todoId = Long.parseLong(getHttpRequest().getParameter("todoId"));

//        // Xóa Todo từ todoService
//        todoService.delete(todoId);

        return "success";
    }

    public List<TodoDto> getTodos() {
        return todolist;
    }
    public void setTodolist(List<TodoDto> todolist) {
        this.todolist = todolist;
    }
}