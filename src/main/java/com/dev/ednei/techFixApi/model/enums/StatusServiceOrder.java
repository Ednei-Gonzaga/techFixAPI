package com.dev.ednei.techFixApi.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum StatusServiceOrder {
    PENDING("Pendente"),
    UNDER_ANALYSIS("Em_Analise"),
    IN_PROGRESS("Em_Andamento"),
    COMPLETED("Concluido"),
    NOT_REPAIRED("Nao_Reparado"),
    CANCELED("Cancelado"),
    DELIVERED("Entregue"),
    WAITING_FOR_PARTS("Aguardando_Pecas");

    private final String portugueseOption;

    StatusServiceOrder(String value) {
        this.portugueseOption = value;
    }

    @JsonCreator
    public StatusServiceOrder fromString(String value){
        for (StatusServiceOrder status: StatusServiceOrder.values()){
            if (status.name().equalsIgnoreCase(value) || portugueseOption.equalsIgnoreCase(value)){
                return status;
            }
        }
        return null;
    }
}
