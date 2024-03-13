package com.atlassian.tutorial.ao.todo.rest;

import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugins.rest.common.security.AnonymousAllowed;
import com.atlassian.tutorial.ao.todo.dto.TodoDto;
import com.atlassian.tutorial.ao.todo.model.Todo;
import com.atlassian.tutorial.ao.todo.service.TodoService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;
@Scanned
@Path("/todo")
public class TodoResource {
    private static final Logger log = LogManager.getLogger(TodoResource.class);
    private final TodoService todoService;

    public TodoResource(TodoService todoService) {
        this.todoService = todoService;
    }

    @GET
    @AnonymousAllowed
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTodos() {
        try {
            List<Todo> todos = todoService.getAllTodos();
            log.info("Number of Todos: " + todos.size()); // Ghi log số lượng Todo

            List<TodoDto> todoDtos = todos.stream()
                    .map(todo -> {
                        TodoDto dto = new TodoDto(todo);
                        log.info("Processing Todo: " + dto); // Ghi log mỗi TodoDto được xử lý
                        return dto;
                    })
                    .collect(Collectors.toList());

            log.info("Number of TodoDtos: " + todoDtos.size()); // Ghi log số lượng TodoDto sau khi xử lý
            return Response.ok(todoDtos).build();
//            List<Todo> todos = todoService.getAllTodos();
//            List<String> test = new ArrayList<>();
//            for (Todo t :
//                    todos) {
//
//                test.add(t.getDescription());
//            }
//            // Chuyển đổi từ List<Todo> sang List<TodoDto> để trả về
//            List<TodoDto> todoDtos = todos.stream()
//                    .map(todo -> new TodoDto(todo)) // Giả sử bạn có constructor phù hợp trong TodoDto
//                    .collect(Collectors.toList());
//            return Response.ok(test).build();
        } catch (Exception ex) {
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            String errorsStr = errors.toString();
            return Response.ok(errorsStr).build();
//            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
//                    .entity(ex.getMessage()).build();
        }
    }
    @POST
    @AnonymousAllowed
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTodo(TodoDto todoDto) {
        Todo todo = todoService.createTodo(
                todoDto.getUsername(),
                todoDto.getSummary(),
                todoDto.getDescription(), todoDto.isComplete());
        if(todo != null){
            return Response.ok(new TodoDto(todo)).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }



    @GET
    @AnonymousAllowed
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTodo(@PathParam("id") int id) {
        Todo todo = todoService.getTodoById(id);
        if (todo != null) {
            return Response.ok(new TodoDto(todo)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @AnonymousAllowed
    @Path("/update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTodo(@PathParam("id") int id, TodoDto todoDto) {
        boolean success = todoService.updateTodo(id,
                todoDto.getUsername(),
                todoDto.getSummary(),
                todoDto.getDescription(), todoDto.isComplete());
        if (success) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @AnonymousAllowed
    @Path("/delete/{id}")
    public Response deleteTodo(@PathParam("id") int id) {
        boolean success = todoService.deleteTodo(id);
        if (success) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
