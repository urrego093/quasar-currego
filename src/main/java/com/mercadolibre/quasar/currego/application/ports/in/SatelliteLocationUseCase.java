package com.mercadolibre.quasar.currego.application.ports.in;

import com.mercadolibre.quasar.currego.domain.model.Satellite;

import java.util.List;

public interface SatelliteLocationUseCase {
    double[] getLocation(double[] locations);

    double[] getLocation(List<Satellite> satelliteDistances);
    String getMessage( List<String[]> messages);

    Boolean updateSatellite( Satellite  satellite);

}
