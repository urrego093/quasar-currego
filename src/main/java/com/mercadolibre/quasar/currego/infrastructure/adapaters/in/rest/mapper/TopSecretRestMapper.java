package com.mercadolibre.quasar.currego.infrastructure.adapaters.in.rest.mapper;

import com.mercadolibre.quasar.currego.domain.model.Satellite;
import com.mercadolibre.quasar.currego.infrastructure.adapaters.in.rest.data.request.SatelliteRequest;
import com.mercadolibre.quasar.currego.infrastructure.adapaters.in.rest.data.request.TopSecretRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface TopSecretRestMapper {
    Satellite toSatellite(SatelliteRequest satelliteRequest);

    List<Satellite> toSatelliteArray (List<SatelliteRequest> satelliteRequestList);

}
