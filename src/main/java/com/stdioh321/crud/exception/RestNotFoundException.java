package com.stdioh321.crud.exception;

import org.springframework.http.HttpStatus;


public class RestNotFoundException extends RestGenericExecption {
    protected String message = "Entity Not Found";
    protected HttpStatus status = HttpStatus.NOT_FOUND;


    public RestNotFoundException(String object, Object rejectedValue) {
        super(object, rejectedValue);
    }


}
