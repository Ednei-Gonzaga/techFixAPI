package com.dev.ednei.techFixApi.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ServiceOrderStatus {
    PENDING("PEDENTE"),
    UNDER_ANALYSIS("EM_ANALISE"),
    WAITING_FOR_PARTS("AGUARDANDO_PECAS"),
    CONCERT_IN_PROGRESS("CONCERTO_EM_ANDAMENTO"),
    COMPLETED("CONCLUIDO"),
    DELIVERED("ENTREGUE"),
    CANCELED("CANCELADO");

    public String portugueseOption;
    private ServiceOrderStatus(String portugueseOption) {
        this.portugueseOption = portugueseOption;
    }


    public static ServiceOrderStatus forValue(String value) {
        for(ServiceOrderStatus status : ServiceOrderStatus.values()) {
            if(status.name().equalsIgnoreCase(value) || status.portugueseOption.equalsIgnoreCase(value) ) {
                return status;
            }
        }
        return null;
    }



}
