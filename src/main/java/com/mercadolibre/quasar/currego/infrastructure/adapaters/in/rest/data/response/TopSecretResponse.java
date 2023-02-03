package com.mercadolibre.quasar.currego.infrastructure.adapaters.in.rest.data.response;

import lombok.Value;

@Value
public class TopSecretResponse {
    PositionResponse position;
    String message;
}
