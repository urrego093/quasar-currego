package com.mercadolibre.quasar.currego.application.ports.in;

import java.util.List;

public interface SatelliteLocationUseCase {
    double[] getLocation(double[] locations);
    String GetMessage( String[]... messages);

}
