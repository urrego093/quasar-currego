package com.mercadolibre.quasar.currego.infrastructure.adapaters.in.rest.exception;

import com.mercadolibre.quasar.currego.domain.exception.DomainNotEnoughSatellitesException;
import com.mercadolibre.quasar.currego.domain.exception.DomainSatelliteNotFoundException;
import com.mercadolibre.quasar.currego.domain.exception.DomainUpdateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class TopSecretControllerAdvice {

    private void logError(RuntimeException e, WebRequest webRequest){
        log.error( this.getClass().getName() + "--> " + webRequest.getContextPath() + " - " +  e.getMessage(), webRequest);
    }

    @ExceptionHandler({DomainNotEnoughSatellitesException.class})
    public ResponseEntity<Object> handleNoEnoughSatellites(RuntimeException e, WebRequest webRequest) {
        logError(e, webRequest);
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "There are not enought satellites to calculate the position, there should be at least 3");

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({DomainSatelliteNotFoundException.class})
    public ResponseEntity<Object> handleNoSatelliteFound(RuntimeException e, WebRequest webRequest) {
        logError(e, webRequest);
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "A problem ocurred finding a Satellite");

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DomainUpdateException.class)
    public ResponseEntity<Object> handleUpdateSatelliteError(RuntimeException e, WebRequest webRequest) {
        logError(e, webRequest);
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "A problem ocurred updating a Satellite");

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}
