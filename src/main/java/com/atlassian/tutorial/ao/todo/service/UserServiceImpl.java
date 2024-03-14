package com.atlassian.tutorial.ao.todo.service;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.tutorial.ao.todo.model.User;
import net.java.ao.Query;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.List;

@Scanned
@Named

public class UserServiceImpl implements UserService{
    @ComponentImport
    private final ActiveObjects ao;

    @Inject
    public UserServiceImpl(ActiveObjects ao) {
        this.ao = ao;
    }

    @Override
    public User findUserById(int userId) {
        return ao.executeInTransaction(() -> {
            // Sử dụng Query để tìm User theo ID
            User[] users = ao.find(User.class, Query.select().where("ID = ?", userId));
            if (users.length > 0) {
                return users[0];
            } else {
                return null; // Không tìm thấy User với ID đã cho
            }
        });
    }

    @Override
    public User findUserByName(String name) {
        return ao.executeInTransaction(() -> {
            // Sử dụng Query để tìm User theo ID
            User[] users = ao.find(User.class, Query.select().where("NAME = ?", name));
            if (users.length > 0) {
                return users[0];
            } else {
                return null; // Không tìm thấy User với ID đã cho
            }
        });
    }

    @Override
    public List<User> findAllUsers() {
        return Arrays.asList(ao.find(User.class, Query.select()));
    }
}
