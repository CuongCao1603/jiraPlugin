package com.atlassian.tutorial.ao.todo.service;

import com.atlassian.activeobjects.tx.Transactional;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.tutorial.ao.todo.model.User;

import java.util.List;

@Scanned
@Transactional
public interface UserService {
    User findUserById(int userId);
    User findUserByName(String name);

    List<User> findAllUsers(); // Thêm phương thức mới
}
