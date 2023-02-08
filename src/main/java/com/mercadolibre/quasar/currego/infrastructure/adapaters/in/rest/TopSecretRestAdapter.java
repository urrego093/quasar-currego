package com.mercadolibre.quasar.currego.infrastructure.adapaters.in.rest;

import com.mercadolibre.quasar.currego.TrackExecutionTimeTarget;
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


    /**
     * Supossing that all satellites in request existe in the db, returns the position of an enemy spaceship and a hidden
     * message calculated using all provided messages
     *
     * There should be at least 3 satellites otherwise method will fail
     * Method could calculate position using more than 3 points but that wasnt a requirement
     *
     * @param request the information of satellites, could
     * @return the POsition and hidden message
     */
    @PostMapping(value = "top-secret")
    @TrackExecutionTimeTarget
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

    /**
     * Updates satellites distance and received message to an enemy spaceship.
     *
     * If the satellite does not exist returns a 404 error
     *
     * @param satelliteRequest the information to persist
     * @param satelliteName the satellite name
     * @return True if information was updated, false if not
     */
    @PostMapping(value = "/top-secret-split/{satelliteName}")
    @TrackExecutionTimeTarget
    public ResponseEntity<Boolean> updateDistance(@RequestBody SatelliteRequest satelliteRequest, @PathVariable String satelliteName){
        Satellite satellite = topSecretRestMapper.toSatellite(satelliteRequest);
        satellite.setName(satelliteName);
        return new ResponseEntity<>( satelliteLocationUseCase.updateSatellite(satellite), HttpStatus.OK);
    }

    /**
     * Using all information stored in the satellite repository
     * computes the position of an enemy spaceship using all satellites location and registered distance to it
     * Also computes a hidden message trying to use the message fragments  captured by each satellite
     * @return the calculated position of an enemy spaceship and a hidden message discovered after joining
     * all available message fragmnts in each satellite
     */
    @GetMapping(value = "/top-secret-split/")
    @TrackExecutionTimeTarget
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
