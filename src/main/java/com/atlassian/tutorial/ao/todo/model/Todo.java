package com.atlassian.tutorial.ao.todo.model;

import net.java.ao.Entity;
import net.java.ao.Preload;
import net.java.ao.schema.NotNull;

@Preload
public interface Todo extends Entity
{
    @NotNull
    void setUser(String username);

    @NotNull
    User getUser();

    void setSummary(String summary);
    String getSummary();

    String getDescription();

    void setDescription(String description);

    boolean isComplete();

    void setComplete(boolean complete);

}
