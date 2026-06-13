package com.dev.ednei.techFixApi.infra.exceptions.errors;

public class InvalidParameterException extends RuntimeException {
    public InvalidParameterException(String message){
        super(message);
    }
}
