package com.mercadolibre.quasar.currego.domain.service;

import com.lemmingapex.trilateration.NonLinearLeastSquaresSolver;
import com.lemmingapex.trilateration.TrilaterationFunction;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.stream.IntStream;


@Component
public  class IntelligenceUtilComponent {


    public double[] calculateTrilateration( double[][] positions,  double[] distances) {
        NonLinearLeastSquaresSolver solver = new NonLinearLeastSquaresSolver(new TrilaterationFunction(positions, distances),
                new LevenbergMarquardtOptimizer());
        LeastSquaresOptimizer.Optimum optimum = solver.solve();
        return optimum.getPoint().toArray();
    }


    //    TODO find a way to order words if posible
    public String mergeMessages(String[]... messages) {

        int maxSize = 0;
        LinkedHashSet<String> finalMessage =  new LinkedHashSet<>();
        for (String[] message: messages) {
            maxSize = Math.max(message.length, maxSize);
        }

        for (String[] message: messages) {
            IntStream.range(0,maxSize)
                    .filter(index -> ( message[index] != null && !message[index].equals("") ) )
                    .mapToObj(index -> finalMessage.add(message[index]))
                    .toList();
        }

        return finalMessage.stream().reduce("", (s, s2) -> s + s2 + " ").trim();
    }
}
