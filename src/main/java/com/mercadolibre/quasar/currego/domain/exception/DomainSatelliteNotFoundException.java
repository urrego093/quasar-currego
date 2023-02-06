package com.mercadolibre.quasar.currego.domain.exception;

public class DomainSatelliteNotFoundException extends  RuntimeException{

    public DomainSatelliteNotFoundException(String message){
        super(message);
    }
}
