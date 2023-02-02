package com.mercadolibre.quasar.currego.infrastructure.adapaters.in.rest;

import com.mercadolibre.quasar.currego.application.ports.in.SatelliteLocationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("satellite")
@RequiredArgsConstructor
public class SatelliteRestAdapter {

    private final SatelliteLocationUseCase satelliteLocationUseCase;

    @GetMapping(value = "location")
    public double[] getLocation (){
        double [] distances = {0,0};
        return satelliteLocationUseCase.getLocation( distances );
    }

    @GetMapping(value = "message")
    public String getMessage (String [] messages){

        return "this is bananas";
    }
}
