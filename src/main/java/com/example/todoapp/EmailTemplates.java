package com.example.todoapp;

public enum EmailTemplates{
    VERIFICATION_CODE_TEMPLATE("verification_code.html"),
    PASSWORD_CHANGE_CONFIRMATION_TEMPLATE("password_change_confirmation.html");


    private final String fileName;

    EmailTemplates(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
