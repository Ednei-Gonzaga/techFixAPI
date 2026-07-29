package com.dev.ednei.techFixApi.DTOS.evolutionApi;

public record SendMessageEvolutionApi(
        String number,
        String text,
        Integer delay
) {
}
