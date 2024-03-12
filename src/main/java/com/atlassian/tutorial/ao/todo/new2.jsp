<%@ page import="java.util.List" %>
<%@ page import="com.atlassian.tutorial.ao.todo.Todo" %>

<%
    List<Todo> todos = (List<Todo>) request.getAttribute("todos");
    for (Todo todo : todos) {
        out.println("<p>" + todo.getDescription() + "</p>");
    }
%>
