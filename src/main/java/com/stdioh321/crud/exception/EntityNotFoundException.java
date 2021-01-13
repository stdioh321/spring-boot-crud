package com.stdioh321.crud.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class EntityNotFoundException extends RuntimeException {
    private String message;
    private String rejectedValue;
    private String object;
    private HttpStatus status = HttpStatus.NOT_FOUND;

    public EntityNotFoundException(String rejectedValue, String object) {
        message = "Entity not found";
        this.rejectedValue = rejectedValue;
        this.object = object;
    }

}
