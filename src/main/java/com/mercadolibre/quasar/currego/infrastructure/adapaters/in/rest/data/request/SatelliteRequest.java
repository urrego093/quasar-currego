package com.mercadolibre.quasar.currego.infrastructure.adapaters.in.rest.data.request;

import lombok.Value;

@Value
public class SatelliteRequest {
    String name;
    double distance;
    String[] message;
}
