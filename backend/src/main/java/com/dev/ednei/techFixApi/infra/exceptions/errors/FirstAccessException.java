package com.dev.ednei.techFixApi.infra.exceptions.errors;

import lombok.Getter;

@Getter
public class FirstAccessException extends RuntimeException {

    private String token;

    public FirstAccessException(String message, String tokenResponse) {
        super(message);
        this.token = tokenResponse;
    }
}
