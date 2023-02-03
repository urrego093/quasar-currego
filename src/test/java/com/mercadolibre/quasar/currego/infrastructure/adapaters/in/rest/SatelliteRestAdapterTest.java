package com.mercadolibre.quasar.currego.infrastructure.adapaters.in.rest;

import com.mercadolibre.quasar.currego.application.ports.in.SatelliteLocationUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class SatelliteRestAdapterTest {
    @Autowired
    private  SatelliteLocationUseCase satelliteLocationUseCase;

    @Test
    void contextLoads() throws Exception {
        Assertions.assertNotNull(satelliteLocationUseCase);
    }

}