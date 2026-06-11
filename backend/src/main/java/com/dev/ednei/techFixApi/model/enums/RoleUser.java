package com.dev.ednei.techFixApi.model.enums;

public enum RoleUser {
    MANAGER("GERENTE"),
    TECHNICAL("TECNICO"),
    ATTENDANT("ATENDENTE");

    public String portugueseOption;

    RoleUser(String portugueseOption) {
        this.portugueseOption = portugueseOption;
    }

    public static RoleUser forValue(String value) {
        for(RoleUser role: RoleUser.values()){
            if(role.name().equalsIgnoreCase(value) || role.portugueseOption.equalsIgnoreCase(value)){
                return role;
            }
        }
        return null;
    }
}
