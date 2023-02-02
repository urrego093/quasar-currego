package com.mercadolibre.quasar.currego.domain.service;

import com.lemmingapex.trilateration.NonLinearLeastSquaresSolver;
import com.lemmingapex.trilateration.TrilaterationFunction;
import com.mercadolibre.quasar.currego.application.ports.in.SatelliteLocationUseCase;
import com.mercadolibre.quasar.currego.domain.model.Satellite;
import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SatelliteService implements SatelliteLocationUseCase {
    @Override
    public double[] getLocation(double[] locations) {
        Satellite kenobi = Satellite.builder().name("kenobi").positions(new double[]{-500, -200}).build();
        Satellite skyWalker = Satellite.builder().name("kenobi").positions(new double[]{100, -100}).build();
        Satellite sato = Satellite.builder().name("kenobi").positions(new double[]{500, 100}).build();

        double[][] positions = new double[][] { kenobi.getPositions(), skyWalker.getPositions(), sato.getPositions() };
        double[] distances = new double[] { 100.0, 115.5, 142.7};

        NonLinearLeastSquaresSolver solver = new NonLinearLeastSquaresSolver(new TrilaterationFunction(positions, distances), new LevenbergMarquardtOptimizer());
        LeastSquaresOptimizer.Optimum optimum = solver.solve();

// the answer
        double[] centroid = optimum.getPoint().toArray();
        return centroid;
    }
}
