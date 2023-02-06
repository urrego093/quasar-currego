package com.mercadolibre.quasar.currego.infrastructure.adapaters.out.dynamo;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMappingException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.mercadolibre.quasar.currego.application.ports.out.SatelliteRepository;
import com.mercadolibre.quasar.currego.application.ports.out.exception.ApplicationOutRepositoryException;
import com.mercadolibre.quasar.currego.domain.model.Satellite;
import com.mercadolibre.quasar.currego.infrastructure.adapaters.out.dynamo.entity.SatelliteEntity;
import com.mercadolibre.quasar.currego.infrastructure.adapaters.out.dynamo.mapper.SatelliteRepositoryMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
@Slf4j
public class SatelliteRepositoryDynamoDbAdapter implements SatelliteRepository {


    private final DynamoDBMapper dynamoDBMapper;
    private final SatelliteRepositoryMapper mapper;

    /**
     * Had to create this utilitary method bc dynamobd caauses errors while storing empoytStrings inside a list or field
     *
     * @param original original List of strings, with caracters to replace
     * @param toReplace  special char to get replaced
     * @param replacement  special char to replace instead of @toReplace
     * @return a new list of String arrays with the chars replaced
     */
    //TODO replace  method usages with a real solution like storing a single array into the db then splitting by commas
    private List<String> fixSpaceErrors(List<String> original, String toReplace, String replacement){
        return original.stream().map(word -> word.isEmpty() || word.equals("@") ? word.replaceAll(toReplace, replacement) : word).toList();
    }

    @Override
    public Optional <List<Satellite> > findAll() {
        List<SatelliteEntity> resultList =  dynamoDBMapper.scan(SatelliteEntity.class, new DynamoDBScanExpression());
        if( resultList != null && !resultList.isEmpty()){
            resultList.forEach(result -> {
                List<String> fixedMessage = fixSpaceErrors( result.getMessage() , "@", "");
                result.setMessage(fixedMessage);
            });
            return Optional.of(mapper.toSatelliteList(resultList));
        }
        else {
            return  Optional.empty();
        }

    }


    @Override
    public Optional<Satellite> findByName(String satelliteName){
        SatelliteEntity queryResult = dynamoDBMapper.load(SatelliteEntity.class, satelliteName);
        if( queryResult != null){
            List<String> fixedMessage = fixSpaceErrors( queryResult.getMessage() , "@", "");
            queryResult.setMessage(fixedMessage);
            return Optional.of(mapper.toSatellite(queryResult));
        }
        else {
            return  Optional.empty();
        }


    }

    @Override
    public void save( Satellite satellite) throws ApplicationOutRepositoryException {
        SatelliteEntity satelliteEntity = mapper.toSatelliteEntity(satellite);
        List<String> fixedMessage = fixSpaceErrors( satelliteEntity.getMessage() , "", "@");
        satelliteEntity.setMessage(fixedMessage);
        try {
            dynamoDBMapper.save(satelliteEntity, DynamoDBMapperConfig.builder().withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.UPDATE_SKIP_NULL_ATTRIBUTES).build() );
        }
        catch (RuntimeException e){
            String message ="An error ocurring while saving the Satellie with name " + satellite.getName() ;
            log.error(message);
            throw new ApplicationOutRepositoryException(message);
        }

    }
}
