package com.dev.ednei.techFixApi.infra.exception.errors;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String message){
        super(message);
    }
}
