package com.stdioh321.crud.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ApiError {
    private HttpStatus status;
    private LocalDateTime timestamp;

    private String exception;
    private String message;
    private String debubMessage;
    private List<ApiSubError> subErrors;

    public ApiError(){
        timestamp = LocalDateTime.now();
    }
    public ApiError(HttpStatus status){
        this();
        this.status = status;
    }
    public ApiError(HttpStatus status, Throwable ex){
        this();
        this.status = status;
        this.message = "Unexpected error";
        this.debubMessage = ex.getLocalizedMessage();
        this.exception = ex.getClass().getName();
    }
    public ApiError(HttpStatus status, String message, Throwable ex){
        this();
        this.status = status;
        this.message = ex.getMessage();
        this.debubMessage = ex.getLocalizedMessage();
        this.exception = ex.getClass().getName();
    }
}

@Data
class ApiSubError{
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    public ApiSubError(String object){
        this.object = object;
    }
    public ApiSubError(String object, String message){
        this.object = object;
        this.message = message;
    }
}
