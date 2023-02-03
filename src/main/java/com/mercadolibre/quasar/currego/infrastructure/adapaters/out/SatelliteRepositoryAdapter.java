package com.mercadolibre.quasar.currego.infrastructure.adapaters.out;

import com.mercadolibre.quasar.currego.application.ports.out.SatelliteRepository;
import com.mercadolibre.quasar.currego.domain.model.Satellite;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class SatelliteRepositoryAdapter implements SatelliteRepository {

    private List<Satellite> localSatellites;
    @PostConstruct
    void setupLocal(){
        this.localSatellites = new ArrayList<>();

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

        this.localSatellites = Arrays.asList(kenobi, skyWalker, sato);
    }

    @Override
    public List<Satellite> findAll() {
        return this.localSatellites;
    }

    @Override
    public Optional<Satellite> findByName(String name){
        return this.localSatellites.stream().filter(satellite -> satellite.getName().equals(name)).findAny();
    }
}
