package com.mercadolibre.quasar.currego.domain.exception;

public class DomainUpdateException extends RuntimeException{
    public DomainUpdateException(String message) {
        super(message);
    }
}
