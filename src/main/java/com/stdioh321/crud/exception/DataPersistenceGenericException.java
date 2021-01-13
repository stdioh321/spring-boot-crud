package com.stdioh321.crud.exception;

import org.springframework.http.HttpStatus;

public class DataPersistenceGenericException extends RestGenericExecption {

    public DataPersistenceGenericException(){
        message = "Error persisting data";
        status = HttpStatus.CONFLICT;
    }
    public DataPersistenceGenericException(String debugMessage, String rejectedValue, String object){
        this();
        this.debugMessage = debugMessage;
        this.rejectedValue = rejectedValue;
        this.object = object;
    }
}
