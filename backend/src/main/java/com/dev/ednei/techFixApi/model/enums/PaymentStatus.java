package com.dev.ednei.techFixApi.model.enums;

public enum PaymentStatus {
    PENDING("PENDENTE"),
    PAID("PAGO"),
    REFUNDED("REEMBOLSADO"),
    CANCELED("CANCELADO");

    public String portugueseOption;

    PaymentStatus(String portugueseOption) {
        this.portugueseOption = portugueseOption;
    }

    public static PaymentStatus forValue(String value) {
        for (PaymentStatus status : PaymentStatus.values()) {
            if (status.name().equalsIgnoreCase(value) || status.portugueseOption.equalsIgnoreCase(value)) {
                return status;
            }
        }
        return null;
    }

}
