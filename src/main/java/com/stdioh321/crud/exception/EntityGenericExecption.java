package com.stdioh321.crud.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class EntityGenericExecption extends RuntimeException{
    private String message;
    private String rejectedValue;
    private String object;
    private HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
}
