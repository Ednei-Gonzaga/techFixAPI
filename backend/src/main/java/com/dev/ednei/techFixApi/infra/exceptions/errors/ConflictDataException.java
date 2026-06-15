package com.dev.ednei.techFixApi.infra.exceptions.errors;

public class ConflictDataException extends RuntimeException{
    public ConflictDataException(String message){
        super(message);
    }
}
