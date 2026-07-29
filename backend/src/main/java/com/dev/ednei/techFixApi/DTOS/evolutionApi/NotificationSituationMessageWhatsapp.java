package com.dev.ednei.techFixApi.DTOS.evolutionApi;

import java.time.LocalDateTime;

public record NotificationSituationMessageWhatsapp(
        String destination,
        String status,
        String detail,
        LocalDateTime dateTimeSend
) {
}
