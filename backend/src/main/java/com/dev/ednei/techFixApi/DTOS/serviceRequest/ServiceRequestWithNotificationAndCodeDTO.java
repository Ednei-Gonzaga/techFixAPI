package com.dev.ednei.techFixApi.DTOS.serviceRequest;

import com.dev.ednei.techFixApi.DTOS.evolutionApi.NotificationSituationMessageWhatsapp;

public record ServiceRequestWithNotificationAndCodeDTO(
        ServiceRequestFullDTO serviceRequest,
        String identificationCode,
        NotificationSituationMessageWhatsapp notificationWhatsapp
) {
}
