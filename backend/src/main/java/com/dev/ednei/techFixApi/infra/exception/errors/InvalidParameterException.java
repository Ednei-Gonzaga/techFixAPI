package com.dev.ednei.techFixApi.infra.exception.errors;

public class InvalidParameterException extends RuntimeException {
    public InvalidParameterException(String message){
        super(message);
    }
}
