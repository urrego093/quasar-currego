package com.mercadolibre.quasar.currego.domain.service;

import com.lemmingapex.trilateration.NonLinearLeastSquaresSolver;
import com.lemmingapex.trilateration.TrilaterationFunction;
import com.mercadolibre.quasar.currego.application.ports.in.SatelliteLocationUseCase;
import com.mercadolibre.quasar.currego.application.ports.out.SatelliteRepository;
import com.mercadolibre.quasar.currego.application.ports.out.exception.ApplicationOutRepositoryException;
import com.mercadolibre.quasar.currego.domain.exception.DomainNotEnoughSatellitesException;
import com.mercadolibre.quasar.currego.domain.exception.DomainSatelliteNotFoundException;
import com.mercadolibre.quasar.currego.domain.exception.DomainUpdateException;
import com.mercadolibre.quasar.currego.domain.model.Satellite;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.IntStream;


@RequiredArgsConstructor
@Component
@Slf4j
public class SatelliteService implements SatelliteLocationUseCase {

    private final SatelliteRepository satelliteRepository;

    private double[] calculateDistances(List<Satellite> satellites) {
        double[][] positions = new double[satellites.size()][];
        double[] distances = new double[satellites.size()];
        for (int i = 0; i < satellites.size(); i++) {
            positions[i] = satellites.get(i).getPositions();
            distances[i] = satellites.get(i).getDistance();
        }

        NonLinearLeastSquaresSolver solver = new NonLinearLeastSquaresSolver(new TrilaterationFunction(positions, distances),
                new LevenbergMarquardtOptimizer());
        LeastSquaresOptimizer.Optimum optimum = solver.solve();
        return optimum.getPoint().toArray();
    }

    /**
     * Givenm a List of String arrays finds a hidden message merging all the arrays
     *
     * @param messages List of messages to merge
     * @return The hidden message
     */
    private String findHiddenMessage(List<String[]> messages) {
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

    @Override


    /**
     * @deprecated
     */
    @Deprecated(since = "05/02/2023")
    public double[] getLocation(double[] distances) throws DomainSatelliteNotFoundException, DomainNotEnoughSatellitesException {

        List<Satellite> satellites = satelliteRepository.findAll().orElseThrow(() ->
                new DomainSatelliteNotFoundException("It was not possible to find any registered satellite."));
        if (satellites.size() < 3) {
            throw new DomainNotEnoughSatellitesException("There must be at least  satelites registered.");
        } else {
            for (int i = 0; i < satellites.size(); i++) {
                satellites.get(i).setDistance(distances[i]);
            }
            return calculateDistances(satellites);
        }


    }

    @Override
    public double[] getLocation(List<Satellite> satelliteDistances) throws DomainSatelliteNotFoundException, DomainNotEnoughSatellitesException {
        if (satelliteDistances.isEmpty() || satelliteDistances.size() < 3){
            throw new DomainNotEnoughSatellitesException("There must be at least 3 satellites provided.");
        }
        List<Satellite> satellites = new ArrayList<>();
        satelliteDistances.forEach(satelliteDistance -> {
            Satellite satellite = satelliteRepository.findByName(satelliteDistance.getName()).orElseThrow(
                    () -> new DomainSatelliteNotFoundException("It was not possible to find satellite with name " + satelliteDistance.getName())
            );
            satellite.setDistance(satelliteDistance.getDistance());
            satellites.add(satellite);

        });
        return calculateDistances(satellites);
    }


    @Override
    public String getMessage(List<String[]> messages) {
        return findHiddenMessage(messages);
    }

    @Override
    public Boolean updateSatellite(Satellite satellite) throws DomainSatelliteNotFoundException, DomainUpdateException {
         Satellite existingSatellite = satelliteRepository.findByName(satellite.getName()).orElseThrow(
                () -> new DomainSatelliteNotFoundException("It was not possible to find satellite with name " + satellite.getName())
        );

        existingSatellite.setDistance(satellite.getDistance());
        existingSatellite.setMessage(satellite.getMessage());
        try {
            satelliteRepository.save(existingSatellite);
            return Boolean.TRUE;
        } catch (ApplicationOutRepositoryException exception) {
            log.error("Could not perform save operation for satellite with name " + existingSatellite.getName());
            return Boolean.FALSE;
        }


    }

    @Override
    public double[] getLocation() throws DomainNotEnoughSatellitesException {
        List<Satellite> satellites = satelliteRepository.findAll().orElseThrow(() ->
                new DomainNotEnoughSatellitesException("It was not possible to find any registered satellite."));

        if (satellites.size() < 3) {
            throw new DomainNotEnoughSatellitesException("There are less than 3 sattelites registered.");
        }
        return calculateDistances(satellites);


    }

    @Override
    public String getMessage() throws DomainNotEnoughSatellitesException {
        List<Satellite> satellites = satelliteRepository.findAll().orElseThrow(() ->
                new DomainNotEnoughSatellitesException("It was not possible to find any registered satellite."));
        if (satellites.size() < 3) {
            throw new DomainNotEnoughSatellitesException("There are less than 3 sattelites registered.");
        }
        List<String[]> messages = satellites.stream().map(Satellite::getMessage).toList();
        return findHiddenMessage(messages);
    }
}
