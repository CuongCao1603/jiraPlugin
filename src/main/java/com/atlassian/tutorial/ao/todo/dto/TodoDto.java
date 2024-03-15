package com.atlassian.tutorial.ao.todo.dto;

import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.tutorial.ao.todo.model.Todo;
import org.codehaus.jackson.annotate.JsonProperty;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.RequiredArgsConstructor;

@Scanned

public class TodoDto {
    private int id;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @JsonProperty("user_id")
    private int userId;
//    private String username;
    private String summary;
    private String description;
    private boolean isComplete;

//     Constructor mặc định
    public TodoDto() {
    }

    // Constructor này sẽ được sử dụng để tạo TodoDto từ một Todo
    public TodoDto(Todo todo) {
        this.id = (int) todo.getID(); // Đảm bảo rằng có phương thức getID() hoặc tương tự
        // Lưu ý: Dựa vào cách bạn thiết kế User, bạn có thể cần điều chỉnh việc lấy username
        // Nếu User là một phần của model Active Objects, bạn cần đảm bảo rằng có phương thức phù hợp để lấy username
//        this.username = todo.getUser() != null ? todo.getUser().getName() : null; // Giả định User có phương thức getName()
//        this.userId = todo.getUser().getID();
        this.userId = todo.getUserId();
        this.summary = todo.getSummary();
        this.description = todo.getDescription();
        this.isComplete = todo.isComplete();
    }

    // Getter và Setter cho mọi trường
    @JsonProperty("id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    @JsonProperty("username")
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }

    @JsonProperty("summary")
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("complete")
    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        this.isComplete = complete;
    }

    @Override
    public String toString() {
        return "TodoDto{" +
                "id=" + id +
//                ", username='" + username + '\'' +
                ", summary='" + summary + '\'' +
                ", description='" + description + '\'' +
                ", isComplete=" + isComplete +
                '}';
    }
}
