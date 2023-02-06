package com.mercadolibre.quasar.currego.application.ports.out.exception;

public class ApplicationOutRepositoryException extends  RuntimeException{
    public ApplicationOutRepositoryException(String message) {
        super(message);
    }
}
