package com.stdioh321.crud.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class EntityNotFoundException extends RuntimeException {
    private String message;
    private String rejectedValue;
    private String object;
    private HttpStatus status = HttpStatus.NOT_FOUND;

    public EntityNotFoundException() {
        message = "Entity not found";
    }

    public EntityNotFoundException(String rejectedValue, String object) {
        this();
        this.rejectedValue = rejectedValue;
        this.object = object;
    }

    public EntityNotFoundException(String rejectedValue) {
        this();
        this.rejectedValue = rejectedValue;
    }

}
