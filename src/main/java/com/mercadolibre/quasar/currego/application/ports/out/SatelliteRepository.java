package com.mercadolibre.quasar.currego.application.ports.out;

import com.mercadolibre.quasar.currego.domain.model.Satellite;

import java.util.List;
import java.util.Optional;

public interface SatelliteRepository {

    Optional <List<Satellite>> findAll();

    Optional<Satellite> findByName(String satelliteName);

    void save(Satellite satellite) ;
}
