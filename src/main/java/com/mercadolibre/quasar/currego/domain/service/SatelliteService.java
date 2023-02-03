package com.mercadolibre.quasar.currego.domain.service;

import com.lemmingapex.trilateration.NonLinearLeastSquaresSolver;
import com.lemmingapex.trilateration.TrilaterationFunction;
import com.mercadolibre.quasar.currego.application.ports.in.SatelliteLocationUseCase;
import com.mercadolibre.quasar.currego.application.ports.out.SatelliteRepository;
import com.mercadolibre.quasar.currego.domain.model.Satellite;
import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.IntStream;


@RequiredArgsConstructor
@Component
public class SatelliteService implements SatelliteLocationUseCase {

    private final SatelliteRepository satelliteRepository;

    @Override
    public double[] getLocation(double[] distances) {

        List<Satellite> satellites = satelliteRepository.findAll();
        double[][] positions = new double[satellites.size()][];
        for (int i = 0; i < satellites.size(); i++) {
            positions[i] = satellites.get(i).getPositions();
        }

        NonLinearLeastSquaresSolver solver = new NonLinearLeastSquaresSolver(new TrilaterationFunction(positions, distances),
                new LevenbergMarquardtOptimizer());
        LeastSquaresOptimizer.Optimum optimum = solver.solve();
        return optimum.getPoint().toArray();

    }


    @Override
    public String getMessage(String[]... messages) {

        int maxSize = 0;
        LinkedHashSet<String> finalMessage = new LinkedHashSet<>();
        for (String[] message : messages) {
            maxSize = Math.max(message.length, maxSize);
        }

        for (String[] message : messages) {
            IntStream.range(0, maxSize)
                    .filter(index -> (message[index] != null && !message[index].equals("")))
                    .mapToObj(index -> finalMessage.add(message[index]))
                    .toList();
        }

        return finalMessage.stream().reduce("", (s, s2) -> s + s2 + " ").trim();
    }

}
