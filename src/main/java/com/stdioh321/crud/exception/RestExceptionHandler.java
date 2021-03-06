package com.stdioh321.crud.exception;


import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({Exception.class})
    protected ResponseEntity handleGenericException(Exception ex) {
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }

    @ExceptionHandler({EntityValidationException.class})
    protected ResponseEntity handleEntityValidationException(EntityValidationException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Validation Error", ex);
        List<ApiSubError> subErrors = new ArrayList<>();
        for (FieldError fieldError : ex.getErrors()) {
            ApiSubError subError = new ApiSubError(fieldError.getObjectName(), fieldError.getDefaultMessage());
            subError.setField(fieldError.getField());
            subError.setRejectedValue(fieldError.getRejectedValue());
            subErrors.add(subError);
        }
        apiError.setSubErrors(subErrors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler({RestGenericExecption.class})
    protected ResponseEntity handleEntityNotFoundException(RestGenericExecption ex) {
        ApiError apiError = new ApiError(ex.getStatus(), ex.getMessage(), ex.getDebugMessage(), ex);
        apiError.setRejectedValue(ex.getRejectedValue());
        apiError.setObject(ex.getObject());
        return ResponseEntity.status(ex.getStatus()).body(apiError);
    }

    @ExceptionHandler({DataPersistenceGenericException.class})
    protected ResponseEntity handleDataPersistenceGenericException(DataPersistenceGenericException ex) {
        ApiError apiError = new ApiError(ex.getStatus(), ex.getMessage(), ex.getDebugMessage(), ex);
        apiError.setRejectedValue(ex.getRejectedValue());
        apiError.setObject(ex.getObject());
        return ResponseEntity.status(ex.getStatus()).body(apiError);
    }

    @ExceptionHandler({EntityNotFoundException.class})
    protected ResponseEntity handleEntityNotFoundException(EntityNotFoundException ex) {
        ApiError apiError = new ApiError(ex.getStatus(), ex.getMessage(), ex);
        apiError.setRejectedValue(ex.getRejectedValue());
        apiError.setObject(ex.getObject());
        return ResponseEntity.status(ex.getStatus()).body(apiError);
    }
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    protected ResponseEntity handleEntityNotFoundException(MethodArgumentTypeMismatchException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Error processing argument", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    protected ResponseEntity handleEntityNotFoundException(HttpRequestMethodNotSupportedException ex) {
        ApiError apiError = new ApiError(HttpStatus.METHOD_NOT_ALLOWED, "Method not allowed", ex);
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(apiError);
    }
}
