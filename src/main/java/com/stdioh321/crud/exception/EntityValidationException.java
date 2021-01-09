package com.stdioh321.crud.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Data

public class EntityValidationException extends RuntimeException{
    private String message = "Validation Error";
    private List<FieldError> errors = new ArrayList<>();

    public EntityValidationException(){}
    public EntityValidationException(String message){
        this.message = message;
    }

    public EntityValidationException(List<FieldError> errors){
        this.errors = errors;
    }
    public EntityValidationException(String message, List<FieldError> errors){
        this.message = message;
        this.errors = errors;
    }
}
