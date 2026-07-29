package com.dev.ednei.techFixApi.model.enums.evolutionAPI;

public enum StatusSendWhatsapp {
    DELIVERED,
    FAILED;

    public static StatusSendWhatsapp toValue(String value) {
        for (StatusSendWhatsapp s : StatusSendWhatsapp.values()) {
            if (s.name().equalsIgnoreCase(value)) {
                return s;
            }
        }
        return null;
    }
}
