package com.atlassian.tutorial.ao.todo.dto;

import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.tutorial.ao.todo.model.User;
import org.codehaus.jackson.annotate.JsonProperty;

@Scanned
public class UserDto {
    private int id;
    private String name;

    // Constructor mặc định
    public UserDto() {
    }

    // Constructor sử dụng để tạo UserDto từ một User
    public UserDto(User user) {
        this.id = (int) user.getID();
        this.name = user.getName();
    }

    // Getter và Setter
    @JsonProperty("id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

