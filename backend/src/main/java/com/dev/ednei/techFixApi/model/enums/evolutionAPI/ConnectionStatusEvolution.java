package com.dev.ednei.techFixApi.model.enums.evolutionAPI;

public enum ConnectionStatusEvolution {
    OPEN,
    CLOSE,
    CONNECTING;

    public static ConnectionStatusEvolution forValue(String value){
        for(ConnectionStatusEvolution status: ConnectionStatusEvolution.values()){
            if(status.name().equalsIgnoreCase(value)){
                return status;
            }
        }
        return null;
    }
}
