package com.dev.ednei.techFixApi.DTOS.serviceOrder;

import com.dev.ednei.techFixApi.DTOS.evolutionApi.NotificationSituationMessageWhatsapp;

public record ServiceOrderDetailAndStateSendWhatsapp(
        ServiceOrderDetailDTO serviceOrder,
        NotificationSituationMessageWhatsapp notificationWhatsapp
) {
}
