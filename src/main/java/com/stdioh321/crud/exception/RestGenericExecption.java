package com.stdioh321.crud.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Data

public class RestGenericExecption extends RuntimeException {

    protected HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    protected String message = "Unexpected Error";
    protected String object;
    protected String rejectedValue;
    protected String debugMessage;


    public RestGenericExecption() {
    }

    public RestGenericExecption(String rejectedValue, Object object) {
        this.rejectedValue = rejectedValue;
        this.object = object.getClass().getSimpleName();
    }
    public RestGenericExecption(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public RestGenericExecption(String message, HttpStatus status, String rejectedValue, String object) {
        this.message = message;
        this.status = status;
        this.rejectedValue = rejectedValue;
        this.object = object;
    }

    public RestGenericExecption(String message, String debugMessage, HttpStatus status, String rejectedValue, String object) {
        this.message = message;
        this.status = status;
        this.rejectedValue = rejectedValue;
        this.object = object;
        this.debugMessage = debugMessage;
    }

    public RestGenericExecption(String message,Exception ex, HttpStatus status, String rejectedValue, String object) {
        this.message = message;
        this.debugMessage = ex.getMessage();
        this.status = status;
        this.rejectedValue = rejectedValue;
        this.object = object;
    }
}
