package com.atlassian.tutorial.ao.todo.actions;

import webwork.action.ActionSupport;

public class ExampleAction extends ActionSupport {
    private String message;

    @Override
    public String execute() throws Exception {
        // Logic xử lý của bạn ở đây
        setMessage("Hello, JIRA!");

        return SUCCESS; // Trả về view tên là "success"
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
