package com.dev.ednei.techFixApi.DTOS.evolutionApi;

public record ResponseCheckNumber(
        String number,
        Boolean exists
) {
}
