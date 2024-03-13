package com.atlassian.tutorial.ao.todo.model;

import net.java.ao.Entity;
import net.java.ao.Preload;

@Preload
public interface User extends Entity
{
    String getName();
    void setName(String name);
}
