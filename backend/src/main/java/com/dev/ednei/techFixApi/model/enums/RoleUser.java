package com.dev.ednei.techFixApi.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum RoleUser {
    ADMIN("administrador"),
    TECHNICIAN("tecnico");

    public final String portugueseOption;

    RoleUser(String value){
        this.portugueseOption = value;
    }

    @JsonCreator
    public static RoleUser fromString(String value){
        for (RoleUser role: RoleUser.values() ) {
            if(role.name().equalsIgnoreCase(value) || role.portugueseOption.equalsIgnoreCase(value)){
                return role;
            }
        }
        return null;
    }
}