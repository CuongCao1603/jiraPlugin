package com.atlassian.tutorial.ao.todo.rest;

import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugins.rest.common.security.AnonymousAllowed;
import com.atlassian.tutorial.ao.todo.dto.TodoDto;
import com.atlassian.tutorial.ao.todo.dto.UserDto;
import com.atlassian.tutorial.ao.todo.model.Todo;
import com.atlassian.tutorial.ao.todo.model.User;
import com.atlassian.tutorial.ao.todo.service.TodoService;
import com.atlassian.tutorial.ao.todo.service.UserService;
//import org.apache.log4j.LogManager;
//import org.apache.log4j.Logger;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import java.util.stream.Collectors;

@Scanned
@Path("/todo")
public class TodoResource {
    //    private static final Logger log = LogManager.getLogger(TodoResource.class);
    private final TodoService todoService;

    private final UserService userService;

    public TodoResource(TodoService todoService, UserService userService) {
        this.todoService = todoService;
        this.userService = userService;
    }


    @GET
    @AnonymousAllowed
    @Path("/user/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        try {
            List<User> users = userService.findAllUsers();

            // Chuyển đổi từ List<User> sang List<UserDto> để trả về
            List<UserDto> userDtos = users.stream()
                    .map(UserDto::new) // Sử dụng constructor của UserDto
                    .collect(Collectors.toList());

            // Trả về userDtos dưới dạng JSON
            return Response.ok(userDtos).build();
        } catch (Exception ex) {
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred: " + ex.getMessage()).build();
        }
    }

    @GET
    @AnonymousAllowed
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTodos() {
        try {
            List<Todo> todos = todoService.getAllTodos();

            // Chuyển đổi từ List<Todo> sang List<TodoDto> để trả về
            List<TodoDto> todoDtos = todos.stream()
                    .map(todo -> new TodoDto(todo)) // Giả sử bạn có constructor phù hợp trong TodoDto
                    .collect(Collectors.toList());

            // Trả về todoDtos dưới dạng JSON
            return Response.ok(todoDtos).build();
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
        try {
            Todo todo = todoService.createTodo(
                    todoDto.getUserId(),
//                    todoDto.getUsername(),
                    todoDto.getSummary(),
                    todoDto.getDescription(),
                    todoDto.isComplete());
//            if (todo != null)
            return Response.ok(new TodoDto(todo)).build();

        } catch (Exception ex) {
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            String errorsStr = errors.toString();
            return Response.ok(errorsStr).build();
//            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
//                    .entity(ex.getMessage()).build();
        }
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
                todoDto.getUserId(),
                todoDto.getSummary(),
                todoDto.getDescription(),
                todoDto.isComplete());
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

    @GET
    @AnonymousAllowed
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchTodosByUsername(@QueryParam("username") String username) {
        try {
            // Giả sử todoService có phương thức searchTodosByUsername để tìm kiếm Todo theo username
            List<Todo> todos = todoService.searchTodosByUsername(username);

            // Chuyển đổi từ List<Todo> sang List<TodoDto> để trả về
            List<TodoDto> todoDtos = todos.stream()
                    .map(todo -> new TodoDto(todo))
                    .collect(Collectors.toList());

            // Trả về todoDtos dưới dạng JSON
            return Response.ok(todoDtos).build();
        } catch (Exception ex) {
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            String errorsStr = errors.toString();
            return Response.ok(errorsStr).build();
//        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
//                .entity(ex.getMessage()).build();
        }
    }

    @GET
    @Path("/paged")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTodosPaged(@QueryParam("page") @DefaultValue("1") int page,
                                     @QueryParam("size") @DefaultValue("4") int size) {
        try {
            List<TodoDto> todoDtos = todoService.getAllTodosPaged(page, size).stream()
                    .map(TodoDto::new) // Giả sử bạn có constructor phù hợp trong TodoDto
                    .collect(Collectors.toList());

            return Response.ok(todoDtos).build();
        } catch (Exception ex) {
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errors.toString()).build();
        }
    }


}
