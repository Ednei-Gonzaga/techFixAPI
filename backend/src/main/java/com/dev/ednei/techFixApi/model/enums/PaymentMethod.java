package com.dev.ednei.techFixApi.model.enums;

public enum PaymentMethod {
    PIX("pix"),
    CREDIT_CARD("cartao_credito"),
    DEBIT_CARD("cartao_debito"),
    MONEY("dinheiro");

    private String portugueseOption;

    PaymentMethod(String portugueseOption){
        this.portugueseOption = portugueseOption;
    }

    public static PaymentMethod forValue(String value){
        for(PaymentMethod method : PaymentMethod.values()){
            if(method.name().equalsIgnoreCase(value) || method.portugueseOption.equalsIgnoreCase(value)){
                return method;
            }
        }
        return null;
    }
}
