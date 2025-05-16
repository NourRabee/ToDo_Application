package com.example.todoapp;

public enum Enums {
    RESET_PASSWORD_TEMPLATE("password_reset.html");

    private final String fileName;

    Enums(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
