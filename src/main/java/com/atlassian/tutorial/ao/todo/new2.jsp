<%@ page import="java.util.List" %>
<%@ page import="com.atlassian.tutorial.ao.todo.model.TodoMo" %>

<%
    List<TodoMo> todos = (List<TodoMo>) request.getAttribute("todos");
    for (TodoMo todo : todos) {
        out.println("<p>" + todo.getDescription() + "</p>");
    }
%>
