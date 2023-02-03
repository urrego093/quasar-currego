package com.mercadolibre.quasar.currego.infrastructure.adapaters.in.rest;

import com.mercadolibre.quasar.currego.application.ports.in.SatelliteLocationUseCase;
import com.mercadolibre.quasar.currego.domain.model.Satellite;
import com.mercadolibre.quasar.currego.infrastructure.adapaters.in.rest.data.request.TopSecretRequest;
import com.mercadolibre.quasar.currego.infrastructure.adapaters.in.rest.data.response.PositionResponse;
import com.mercadolibre.quasar.currego.infrastructure.adapaters.in.rest.data.response.TopSecretResponse;
import com.mercadolibre.quasar.currego.infrastructure.adapaters.in.rest.mapper.TopSecretRestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController(value = "/")
@RequiredArgsConstructor
public class TopSecretRestAdapter {

    private final TopSecretRestMapper topSecretRestMapper;
    private final SatelliteLocationUseCase satelliteLocationUseCase;


    @PostMapping(value = "topSecret")
    TopSecretResponse findEnemySpaceShip(@RequestBody TopSecretRequest request) {
        List<Satellite> satellites = topSecretRestMapper.toSatelliteArray(request.getSatellites());
        List<String[]> messages = satellites.stream().map(satellite -> satellite.getMessage()).collect(Collectors.toList());

        double[] positions = satelliteLocationUseCase.getLocation(satellites);
        String hiddenMessage = satelliteLocationUseCase.getMessage(messages);

        PositionResponse positionResponse = PositionResponse.builder()
                .x(positions[0]).y(positions[1]).build();
        return TopSecretResponse.builder().position(positionResponse).message(hiddenMessage).build();
    }
}
