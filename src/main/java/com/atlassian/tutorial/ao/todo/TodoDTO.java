package com.atlassian.tutorial.ao.todo;

import net.java.ao.schema.NotNull;

public class TodoDTO {
    private int id;
    private String description;
    private boolean complete;

    public TodoDTO() {
    }

    public TodoDTO(String description, boolean complete) {
        this.description = description;
        this.complete = complete;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }
}
