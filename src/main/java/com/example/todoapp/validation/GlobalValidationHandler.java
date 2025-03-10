package com.example.todoapp.validation;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice // telling spring that this class will handle exceptions globally for all controllers.
public class GlobalValidationHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>(); // This is used to store field names and their corresponding validation error messages.

        for (ObjectError error : e.getBindingResult().getAllErrors()) {
            String field = (error instanceof FieldError) ? ((FieldError) error).getField() : error.getObjectName(); // "?" shortcut for if-else statement
            // FieldError means the validation error is related to a specific field in your DTO.
            // ObjectError means the validation error applies to the whole object (not just one field)
            errors.put(field, error.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(errors);
    }
}


// Validations at entity level
// jwt