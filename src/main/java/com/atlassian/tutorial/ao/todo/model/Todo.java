package com.atlassian.tutorial.ao.todo.model;

import net.java.ao.Entity;
//import net.java.ao.OneToMany;
import net.java.ao.Preload;
import net.java.ao.schema.NotNull;
//import net.java.ao.ManyToMany;
import org.codehaus.jackson.annotate.JsonProperty;

@Preload
public interface Todo extends Entity
{
//    @JsonProperty("user_id")
    void setUserId(int userId);
    User getUserId();
//    @NotNull
    void setUser(String username);

//    @NotNull
//    @OneToMany
    User getUser();

    void setSummary(String summary);
    String getSummary();

    String getDescription();

    void setDescription(String description);

    boolean isComplete();

    void setComplete(boolean complete);

}
