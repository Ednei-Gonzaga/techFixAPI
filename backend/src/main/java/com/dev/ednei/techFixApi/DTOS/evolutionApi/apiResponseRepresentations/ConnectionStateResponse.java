package com.dev.ednei.techFixApi.DTOS.evolutionApi.apiResponseRepresentations;

public record ConnectionStateResponse(
        InstanceInfo instance
) {
    public record InstanceInfo(
            String instanceName,
            String state
    ) {
    }
}
