package com.dev.ednei.techFixApi.infra.exceptions.errors;

public class UnprocessableEntityException extends RuntimeException {
    public UnprocessableEntityException(String message) {
        super(message);
    }
}
