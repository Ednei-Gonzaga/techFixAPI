package com.dev.ednei.techFixApi.infra.exceptions.errors;

import lombok.Getter;

@Getter
public class FirstAccessException extends RuntimeException {

    private String token;

    public FirstAccessException(String tokenResponse) {
        this.token = tokenResponse;
    }
}
