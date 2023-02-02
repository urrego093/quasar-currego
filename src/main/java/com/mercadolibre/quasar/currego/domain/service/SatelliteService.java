package com.mercadolibre.quasar.currego.domain.service;

import com.lemmingapex.trilateration.NonLinearLeastSquaresSolver;
import com.lemmingapex.trilateration.TrilaterationFunction;
import com.mercadolibre.quasar.currego.application.ports.in.SatelliteLocationUseCase;
import com.mercadolibre.quasar.currego.domain.model.Satellite;
import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Component
public class SatelliteService implements SatelliteLocationUseCase {
    @Override
    public double[] getLocation(double[] distances) {
        Satellite kenobi = Satellite.builder().name("kenobi").positions(new double[]{-500, -200}).build();
        Satellite skyWalker = Satellite.builder().name("kenobi").positions(new double[]{100, -100}).build();
        Satellite sato = Satellite.builder().name("kenobi").positions(new double[]{500, 100}).build();

        double[][] positions = new double[][] { kenobi.getPositions(), skyWalker.getPositions(), sato.getPositions() };


        NonLinearLeastSquaresSolver solver = new NonLinearLeastSquaresSolver(new TrilaterationFunction(positions, distances),
                new LevenbergMarquardtOptimizer());
        LeastSquaresOptimizer.Optimum optimum = solver.solve();

// the answer
        double[] centroid = optimum.getPoint().toArray();
        return centroid;
    }


//    TODO find a way to order words if posible
    @Override
    public String GetMessage(String[]... messages) {

        int maxSize = 0;
        LinkedHashSet<String> finalMessage =  new LinkedHashSet<>();
        for (String[] message: messages) {
            maxSize = message.length > maxSize? message.length : maxSize;
        }

        for (String[] message: messages) {
            IntStream.range(0,maxSize)
                    .filter(index -> ( message[index] != null && !message[index].equals("") ) )
                    .mapToObj(index -> finalMessage.add(message[index]))
                    .collect(Collectors.toList());
        }

        String result  = finalMessage.stream().reduce("", (s, s2) -> s + s2 + " ").trim();

        return result;
    }


}
