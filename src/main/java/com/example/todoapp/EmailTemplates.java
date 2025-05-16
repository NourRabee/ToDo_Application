package com.example.todoapp;

public enum EmailTemplates{
    RESET_PASSWORD_TEMPLATE("password_reset.html");

    private final String fileName;

    EmailTemplates(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
