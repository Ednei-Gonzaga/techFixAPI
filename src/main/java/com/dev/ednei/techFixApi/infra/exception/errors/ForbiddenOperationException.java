package com.dev.ednei.techFixApi.infra.exception.errors;

public class ForbiddenOperationException extends RuntimeException{
    public ForbiddenOperationException(String message){
        super(message);
    }
}
