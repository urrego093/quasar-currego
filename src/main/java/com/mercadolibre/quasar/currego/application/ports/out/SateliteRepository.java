package com.mercadolibre.quasar.currego.application.ports.out;

import com.mercadolibre.quasar.currego.domain.model.Satellite;

import java.util.List;

public interface SateliteRepository {

    List<Satellite> getTestSatellites();
}
