package com.mercadolibre.quasar.currego.infrastructure.adapaters.in.rest.data.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PositionResponse {
    double x;
    double y;
}
