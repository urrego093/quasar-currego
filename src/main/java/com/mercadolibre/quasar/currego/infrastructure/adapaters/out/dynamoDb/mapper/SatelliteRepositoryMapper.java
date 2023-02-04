package com.mercadolibre.quasar.currego.infrastructure.adapaters.out.dynamoDb.mapper;

import com.mercadolibre.quasar.currego.domain.model.Satellite;
import com.mercadolibre.quasar.currego.infrastructure.adapaters.out.dynamoDb.entity.SatelliteEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface SatelliteRepositoryMapper {
    Satellite toSatellite (SatelliteEntity satellite);
    List<Satellite> toSatelliteList (List<SatelliteEntity> satelliteEntityList);

    double[] toDoubleArray( List<Double> doubleList);
}
