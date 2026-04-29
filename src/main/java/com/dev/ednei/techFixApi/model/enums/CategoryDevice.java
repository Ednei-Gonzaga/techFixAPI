package com.dev.ednei.techFixApi.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum CategoryDevice {
        CELLPHONE,
        NOTEBOOK,
        TABLET,
        DESKTOP;

    @JsonCreator
    public static CategoryDevice fromString(String value){
            for(CategoryDevice category: CategoryDevice.values()){
                if(category.name().equalsIgnoreCase(value)) {
                    return category;
                }
            }
            return null;
    }

}
