package com.mercadolibre.quasar.currego.domain.service;

import com.mercadolibre.quasar.currego.application.ports.out.SatelliteRepository;
import com.mercadolibre.quasar.currego.domain.model.Satellite;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class SatelliteServiceTest {

    @InjectMocks
    private SatelliteService satelliteService;

    @Mock
    private SatelliteRepository satelliteRepository;

    @BeforeEach
    void setUp(){
        Satellite kenobi = Satellite.builder()
                .name("kenobi")
                .positions(new double[]{-500, -200})
                .message(new String[] {"este", "", "", "mensaje", ""})
                .build();
        Satellite skyWalker = Satellite.builder()
                .name("skywalker")
                .positions(new double[]{100, -100})
                .message(new String[] {"", "es", "", "", "secreto"})
                .build();
        Satellite sato = Satellite.builder().name("sato")
                .positions(new double[]{500, 100})
                .message(new String[] {"este", "", "un", "", ""})
                .build();

        List<Satellite> satellites =Arrays.asList(kenobi, skyWalker, sato);
        Mockito.when(satelliteRepository.findAll())
                .thenReturn(Optional.of(satellites));
    }



    @Test
    void getLocation_use_local_data() {
        double[] distances = new double[] { 100.0, 115.5, 142.7};
        double[] expected = new double[] {-58.315252587138595,
                -69.55141837312165};

        Assertions.assertArrayEquals(expected, satelliteService.getLocation(distances));

    }

//    TODO implement

    @Test
    void getMessage_dummy_data() {
        String[] message1 = {"este", "", "", "mensaje", ""};
        String[] message2 = {"", "es", "", "", "secreto"};
        String[] message3 = {"este", "", "un", "", ""};

        Assertions.assertEquals("este mensaje es secreto un",
                satelliteService.getMessage(List.of(message1, message2, message3)));
    }
}