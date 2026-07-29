package com.dev.ednei.techFixApi.DTOS.evolutionApi;

import com.fasterxml.jackson.annotation.JsonAlias;

public record InstanceDetailResponse(
        String name,
        String connectionStatus,

        @JsonAlias("ownerJid")
        String number,

        String integration
) {
    public InstanceDetailResponse(InstanceDetailResponse response) {
        this(response.name(), response.connectionStatus(), response.number() != null ? response.number().replaceAll("[^0-9]", "") : null, response.integration());
    }
}
