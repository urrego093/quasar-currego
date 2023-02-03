package com.mercadolibre.quasar.currego.infrastructure.adapaters.in.rest;

import com.mercadolibre.quasar.currego.application.ports.in.SatelliteLocationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("satellite")
@RequiredArgsConstructor
public class SatelliteRestAdapter {

    private final SatelliteLocationUseCase satelliteLocationUseCase;

    @GetMapping(value = "location")
    public double[] getLocation (){
        double[] distances = new double[] { 100.0, 115.5, 142.7};
        return satelliteLocationUseCase.getLocation( distances );
    }

    @GetMapping(value = "message")
    public String getMessage (){
        String[] message1 = {"este", "", "", "mensaje", ""};
        String[] message2 = {"", "es", "", "", "secreto"};
        String[] message3 = {"este", "", "un", "", ""};


        return satelliteLocationUseCase.getMessage(message1, message2, message3);
    }
}
