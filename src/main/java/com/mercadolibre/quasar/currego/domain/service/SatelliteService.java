package com.mercadolibre.quasar.currego.domain.service;

import com.mercadolibre.quasar.currego.application.ports.in.SatelliteLocationUseCase;
import com.mercadolibre.quasar.currego.application.ports.out.SateliteRepository;
import com.mercadolibre.quasar.currego.domain.model.Satellite;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@RequiredArgsConstructor
@Component
public class SatelliteService implements SatelliteLocationUseCase {

    private final IntelligenceUtilComponent intelligenceUtilComponent;
    private final SateliteRepository sateliteRepository;

    @Override
    public double[] getLocation(double[] distances) {

        List<Satellite> satellites = sateliteRepository.getTestSatellites();
        double[][] positions = new double[satellites.size()][];
        for (int i = 0 ; i < satellites.size() ; i++){
            positions[i] = satellites.get(i).getPositions();
        }

        return intelligenceUtilComponent.calculateTrilateration(positions, distances);
    }


    @Override
    public String getMessage(String[]... messages) {
       return  intelligenceUtilComponent.mergeMessages(messages);
    }

}
