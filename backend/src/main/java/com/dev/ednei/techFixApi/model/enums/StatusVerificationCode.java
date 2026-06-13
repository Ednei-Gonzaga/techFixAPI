package com.dev.ednei.techFixApi.model.enums;

public enum StatusVerificationCode {
    ACTIVE("ATIVO"),
    USED("USADO");

    public final String portugueseOption;

    StatusVerificationCode(String value) {
        this.portugueseOption = value;
    }

    public static StatusVerificationCode forValur(String value){
        for (StatusVerificationCode status : StatusVerificationCode.values()) {
            if(status.portugueseOption.equals(value) || status.name().equals(value)){
                return status;
            }
        }
        return null;
    }


}
