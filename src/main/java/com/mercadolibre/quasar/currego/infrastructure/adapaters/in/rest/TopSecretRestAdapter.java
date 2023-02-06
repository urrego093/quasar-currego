package com.mercadolibre.quasar.currego.infrastructure.adapaters.in.rest;

import com.mercadolibre.quasar.currego.TrackExecutionTimeASpect;
import com.mercadolibre.quasar.currego.application.ports.in.SatelliteLocationUseCase;
import com.mercadolibre.quasar.currego.domain.model.Satellite;
import com.mercadolibre.quasar.currego.infrastructure.adapaters.in.rest.data.request.SatelliteRequest;
import com.mercadolibre.quasar.currego.infrastructure.adapaters.in.rest.data.request.TopSecretRequest;
import com.mercadolibre.quasar.currego.infrastructure.adapaters.in.rest.data.response.PositionResponse;
import com.mercadolibre.quasar.currego.infrastructure.adapaters.in.rest.data.response.TopSecretResponse;
import com.mercadolibre.quasar.currego.infrastructure.adapaters.in.rest.mapper.TopSecretRestMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController(value = "/")
@RequiredArgsConstructor
@Slf4j
public class TopSecretRestAdapter {

    private final TopSecretRestMapper topSecretRestMapper;
    private final SatelliteLocationUseCase satelliteLocationUseCase;


    @PostMapping(value = "topSecret")
    @TrackExecutionTimeASpect
    public ResponseEntity<TopSecretResponse> findEnemySpaceShip(@RequestBody TopSecretRequest request) {
        List<Satellite> satellites = topSecretRestMapper.toSatelliteArray(request.getSatellites());
        List<String[]> messages = satellites.stream().map( Satellite::getMessage).toList();

        double[] positions = satelliteLocationUseCase.getLocation(satellites);
        String hiddenMessage = satelliteLocationUseCase.getMessage(messages);

        PositionResponse positionResponse = PositionResponse.builder()
                .x(positions[0]).y(positions[1]).build();
        TopSecretResponse topSecretResponse =  TopSecretResponse.builder().position(positionResponse).message(hiddenMessage).build();

        return new ResponseEntity<>(topSecretResponse, HttpStatus.OK);

    }

    @PostMapping(value = "/topSecret_split/{satelliteName}")
    @TrackExecutionTimeASpect
    public ResponseEntity<Boolean> updateDistance(@RequestBody SatelliteRequest satelliteRequest, @PathVariable String satelliteName){
        Satellite satellite = topSecretRestMapper.toSatellite(satelliteRequest);
        satellite.setName(satelliteName);
        return new ResponseEntity<>( satelliteLocationUseCase.updateSatellite(satellite), HttpStatus.OK);
    }

    @GetMapping(value = "/topSecret_split/")
    @TrackExecutionTimeASpect
    public ResponseEntity<TopSecretResponse> getDistance(){

//TODO this two methods could be combined
        double[] positions = satelliteLocationUseCase.getLocation();
        String hiddenMessage = satelliteLocationUseCase.getMessage();

        PositionResponse positionResponse = PositionResponse.builder()
                .x(positions[0]).y(positions[1]).build();

        TopSecretResponse  topSecretResponse =  TopSecretResponse.builder().position(positionResponse).message(hiddenMessage).build();
        return new ResponseEntity<>( topSecretResponse, HttpStatus.OK);
    }

}
