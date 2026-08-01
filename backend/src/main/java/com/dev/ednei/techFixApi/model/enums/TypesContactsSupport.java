package com.dev.ednei.techFixApi.model.enums;

public enum TypesContactsSupport {
    EMAIL,
    PHONE,
    WHATSAPP;

    public static TypesContactsSupport toValue(String value) {
        for (TypesContactsSupport type : TypesContactsSupport.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        return null;
    }
}
