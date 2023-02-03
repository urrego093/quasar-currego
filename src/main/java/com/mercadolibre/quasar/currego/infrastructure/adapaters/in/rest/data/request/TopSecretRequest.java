package com.mercadolibre.quasar.currego.infrastructure.adapaters.in.rest.data.request;

import lombok.Value;

import java.util.List;

@Value
public class TopSecretRequest {
    List<SatelliteRequest> satellites;
}
