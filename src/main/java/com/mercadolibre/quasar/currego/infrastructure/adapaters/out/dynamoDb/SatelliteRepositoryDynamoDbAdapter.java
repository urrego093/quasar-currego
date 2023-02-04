package com.mercadolibre.quasar.currego.infrastructure.adapaters.out.dynamoDb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.mercadolibre.quasar.currego.application.ports.out.SatelliteRepository;
import com.mercadolibre.quasar.currego.domain.model.Satellite;
import com.mercadolibre.quasar.currego.infrastructure.adapaters.out.dynamoDb.entity.SatelliteEntity;
import com.mercadolibre.quasar.currego.infrastructure.adapaters.out.dynamoDb.mapper.SatelliteRepositoryMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SatelliteRepositoryDynamoDbAdapter implements SatelliteRepository {

    private final DynamoDBMapper dynamoDBMapper;
    private final SatelliteRepositoryMapper satelliteRepositoryMapper;

    @Override
    public List<Satellite> findAll() {
        List<SatelliteEntity> result =  dynamoDBMapper.scan(SatelliteEntity.class, new DynamoDBScanExpression());
        return satelliteRepositoryMapper.toSatelliteList(result);
    }


    @Override
    public Optional<Satellite> findByName(String name){
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withS(name));
        DynamoDBQueryExpression<SatelliteEntity> queryExpression = new DynamoDBQueryExpression<SatelliteEntity>()
                .withKeyConditionExpression("satelliteName  = :val1")
                .withExpressionAttributeValues(eav);
        List<SatelliteEntity> queryResult = dynamoDBMapper.query(SatelliteEntity.class, queryExpression);
        List<Satellite> response = satelliteRepositoryMapper.toSatelliteList(queryResult);


        if(queryResult.size() == 1){
            return  Optional.of( satelliteRepositoryMapper.toSatellite(queryResult.get(0)  ) );
        }
        else {
            return  Optional.empty();
        }

    }
}
