package com.dev.ednei.techFixApi.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.management.relation.Role;

public enum RoleUser {
    ADMIN("administrador"),
    TECHNICIAN("tecnico");

    public String portugueseOption;

    RoleUser(String value){
        this.portugueseOption = value;
    }

    @JsonCreator
    public  RoleUser fromString(String value){
        for (RoleUser role: RoleUser.values() ) {
            if(role.name().equalsIgnoreCase(value) || portugueseOption.equalsIgnoreCase(value)){
                return role;
            }
        }
        return null;
    }
}