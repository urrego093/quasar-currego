package com.mercadolibre.quasar.currego.infrastructure.adapaters.out.dynamoDb;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.mercadolibre.quasar.currego.application.ports.out.SatelliteRepository;
import com.mercadolibre.quasar.currego.domain.model.Satellite;
import com.mercadolibre.quasar.currego.infrastructure.adapaters.out.dynamoDb.entity.SatelliteEntity;
import com.mercadolibre.quasar.currego.infrastructure.adapaters.out.dynamoDb.mapper.SatelliteRepositoryMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class SatelliteRepositoryDynamoDbAdapter implements SatelliteRepository {


    private final DynamoDBMapper dynamoDBMapper;
    private final SatelliteRepositoryMapper mapper;

    /**
     * Had to create this utilitary method bc dynamobd caauses errors while storing empoytStrings inside a list or field
     *
     * @param original
     * @param toReplace
     * @param replacement
     * @return
     */
    private List<String> fixSpaceErrors(List<String> original, String toReplace, String replacement){
        return original.stream().map(word -> word.isEmpty() ? word.replaceAll(toReplace, replacement) : word).toList();
    }

    @Override
    public List<Satellite> findAll() {
        List<SatelliteEntity> resultList =  dynamoDBMapper.scan(SatelliteEntity.class, new DynamoDBScanExpression());
        if( resultList != null){
            resultList.forEach(result -> {
                List<String> fixedMessage = fixSpaceErrors( result.getMessage() , "@", "");
                result.setMessage(fixedMessage);
            });
        }
        return mapper.toSatelliteList(resultList);
    }


    @Override
    public Optional<Satellite> findByName(String satelliteName){
        SatelliteEntity queryResult = dynamoDBMapper.load(SatelliteEntity.class, satelliteName);
        if( queryResult != null){
            List<String> fixedMessage = fixSpaceErrors( queryResult.getMessage() , "@", "");
            queryResult.setMessage(fixedMessage);
        }
        return Optional.of(mapper.toSatellite(queryResult));

    }

    @Override
    public void save( Satellite satellite) {
        SatelliteEntity satelliteEntity = mapper.toSatelliteEntity(satellite);
        List<String> fixedMessage = fixSpaceErrors( satelliteEntity.getMessage() , "", "@");
        satelliteEntity.setMessage(fixedMessage);
        dynamoDBMapper.save(satelliteEntity, new DynamoDBMapperConfig(DynamoDBMapperConfig.SaveBehavior.UPDATE_SKIP_NULL_ATTRIBUTES));
    }
}
