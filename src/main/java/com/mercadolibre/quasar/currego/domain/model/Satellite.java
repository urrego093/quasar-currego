package com.mercadolibre.quasar.currego.domain.model;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
public class Satellite {
    String name;
    double[] positions;
    double distance;
    String [] message;
}
