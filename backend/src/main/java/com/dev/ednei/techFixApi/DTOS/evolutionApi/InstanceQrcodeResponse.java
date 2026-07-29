package com.dev.ednei.techFixApi.DTOS.evolutionApi;

public record InstanceQrcodeResponse(
        String pairingCode,
        String code,
        String base64,
        Integer count
) {
}
