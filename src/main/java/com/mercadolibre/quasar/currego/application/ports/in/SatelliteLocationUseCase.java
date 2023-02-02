package com.mercadolibre.quasar.currego.application.ports.in;

public interface SatelliteLocationUseCase {
    double[] getLocation(double[] locations);
}
