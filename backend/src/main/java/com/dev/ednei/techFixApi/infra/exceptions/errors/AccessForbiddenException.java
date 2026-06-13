package com.dev.ednei.techFixApi.infra.exceptions.errors;

public class AccessForbiddenException extends RuntimeException{
    public AccessForbiddenException(String message){
        super(message);
    }
}
