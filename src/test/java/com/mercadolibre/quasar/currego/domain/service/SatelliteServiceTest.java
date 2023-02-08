package com.mercadolibre.quasar.currego.domain.service;

import com.mercadolibre.quasar.currego.application.ports.out.SatelliteRepository;
import com.mercadolibre.quasar.currego.domain.exception.DomainNotEnoughSatellitesException;
import com.mercadolibre.quasar.currego.domain.model.Satellite;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
    void setUp() {
        Satellite kenobi = Satellite.builder()
                .name("kenobi")
                .positions(new double[]{-500, -200})
                .message(new String[]{"este", "", "", "mensaje", ""})
                .build();
        Satellite skyWalker = Satellite.builder()
                .name("skywalker")
                .positions(new double[]{100, -100})
                .message(new String[]{"", "es", "", "", "secreto"})
                .build();
        Satellite sato = Satellite.builder().name("sato")
                .positions(new double[]{500, 100})
                .message(new String[]{"este", "", "un", "", ""})
                .build();

        List<Satellite> satellites = Arrays.asList(kenobi, skyWalker, sato);
        Mockito.when(satelliteRepository.findAll())
                .thenReturn(Optional.of(satellites));
    }


    @Test
    void getLocation_use_local_data() {
        double[] distances = new double[]{100.0, 115.5, 142.7};
        double[] expected = new double[]{-58.315252587138595,
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

    @Test
    void getLocation() {
        Assertions.assertDoesNotThrow(() -> satelliteService.getLocation());
    }

    @Test
    void updateSatellite_exist() {
        Satellite alf = Satellite.builder()
                .name("alf")
                .positions(new double[]{-500, -200})
                .message(new String[]{"este", "", "", "mensaje", ""})
                .build();
        Mockito.when(satelliteRepository.findByName("alf"))
                .thenReturn(Optional.of(alf));

        Optional<Satellite> satellite = satelliteRepository.findByName("alf");
        Assertions.assertTrue(satellite.isPresent());
    }

    @Test
    void updateSatellite_not_exist() {

        Mockito.when(satelliteRepository.findByName("alf"))
                .thenReturn(Optional.empty());

        Optional<Satellite> satellite = satelliteRepository.findByName("alf");
        Assertions.assertTrue(satellite.isEmpty());
    }

    @Test
    void testGetLocation_at_least_3() {
        //uses method in default
        Assertions.assertDoesNotThrow(() -> satelliteService.getLocation());
    }

    @Test
    void testGetLocation_less_than_3_1_only() {
        mock_find_all_1();
        Assertions.assertThrows(DomainNotEnoughSatellitesException.class, () -> satelliteService.getLocation());
    }

    @Test
    void testGetLocation_none() {
        mock_satelliteRepository_find_all_none();
        Assertions.assertThrows(DomainNotEnoughSatellitesException.class, () -> satelliteService.getLocation());
    }

    @Test
    void getMessage_at_least_3() {
        Assertions.assertDoesNotThrow(() -> satelliteService.getMessage());
    }

    @Test
    void getMessage_less_than_3_1_only() {
        mock_find_all_1();
        Assertions.assertThrows(DomainNotEnoughSatellitesException.class, () -> satelliteService.getMessage());
    }


    @Test
    void getMessage_none() {
        mock_satelliteRepository_find_all_none();
        Assertions.assertThrows(DomainNotEnoughSatellitesException.class, () -> satelliteService.getMessage());
    }

    private void mock_find_all_1() {
        Satellite meli = Satellite.builder().name("meli")
                .positions(new double[]{500, 100})
                .message(new String[]{"este", "", "un", "", ""})
                .build();

        List<Satellite> satellites = Arrays.asList(meli);
        Mockito.when(satelliteRepository.findAll())
                .thenReturn(Optional.of(satellites));
    }

    private void mock_satelliteRepository_find_all_none() {
        Mockito.when(satelliteRepository.findAll())
                .thenReturn(Optional.empty());
    }
}