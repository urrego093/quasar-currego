package com.mercadolibre.quasar.currego.infrastructure.adapaters.out.dynamoDb.mapper;

import com.mercadolibre.quasar.currego.domain.model.Satellite;
import com.mercadolibre.quasar.currego.infrastructure.adapaters.out.dynamoDb.entity.SatelliteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface SatelliteRepositoryMapper {
    @Mapping(source = "satelliteName", target = "name")
    Satellite toSatellite (SatelliteEntity satel1lite);

    @Mapping( target= "satelliteName",  source= "name")
    SatelliteEntity toSatelliteEntity (Satellite satellite);
    List<Satellite> toSatelliteList (List<SatelliteEntity> satelliteEntityList);

    double[] toDoubleArray( List<Double> doubleList);
}
