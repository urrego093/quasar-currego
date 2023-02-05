package com.mercadolibre.quasar.currego.application.ports.in;

import com.mercadolibre.quasar.currego.domain.model.Satellite;

import java.util.List;

public interface SatelliteLocationUseCase {

    String getMessage();

    /**
     * Given n messages, tries to find the hidden message in it
     *
     * @param messages Messages to merge, all empty spaces are ignored
     * @return the hidden message
     */
    String getMessage( List<String[]> messages);



    /**
     * Searches all Sattelites in the repository, position, messages and distances to X given point
     * then finds the given
     * @return the calculated position considering all satellites data
     */
    double[] getLocation();


    /**
     * Supposing the existence of at least n satellites, with n > 3
     *      * Computes the location of an unknown point given the params
     * @param distances A random number of distances to be mapped
     * @return the calculated position considering all satellites data and provided distanmces
     *
     * @deprecated
     */
    @Deprecated(since = "05/02/2023")
    double[] getLocation(double[] distances);

    /**
     * Supposing the existence of at least n satellites, with n > 3
     * Computes the location of an unknown point given existing positions in the repository plus
     * the provided distances
     *
     * @param satelliteDistances Data of n satellites, satellite name specified, id a
     *                           name doesnt exist an error may be thrown
     * @return the calculated location between all satellites
     */
    double[] getLocation(List<Satellite> satelliteDistances);


    /**
     * Searches the given sattelite by name and updates corresponding information in the
     * repository
     * @param satellite The new information to update
     * @return A booleran values if the operation succeded or  not
     */
    Boolean updateSatellite( Satellite  satellite);


}
