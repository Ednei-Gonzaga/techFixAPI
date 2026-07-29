package com.dev.ednei.techFixApi.DTOS.evolutionApi;

public record InstanceCreateRequest(
        String instanceName,
        Boolean qrcode,
        String integration
) {
}
