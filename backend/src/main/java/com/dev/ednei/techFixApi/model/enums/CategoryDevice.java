package com.dev.ednei.techFixApi.model.enums;

public enum CategoryDevice {
    SMARTPHONE,
    NOTEBOOK,
    DESKTOP,
    TABLET;

    public static CategoryDevice toString(String value){
        for (CategoryDevice c : CategoryDevice.values()){
            if(c.name().equalsIgnoreCase(value)){
                return c;
            }
        }
        return null;
    }
}
