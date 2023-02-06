package com.mercadolibre.quasar.currego.domain.exception;

public class DomainNotEnoughSatellitesException extends RuntimeException{

    public DomainNotEnoughSatellitesException(String message) {
        super(message);
    }
}
