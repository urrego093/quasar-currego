package com.mercadolibre.quasar.currego.infrastructure.adapaters.out.dynamoDb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.mercadolibre.quasar.currego.application.ports.out.SatelliteRepository;
import com.mercadolibre.quasar.currego.domain.model.Satellite;
import com.mercadolibre.quasar.currego.infrastructure.adapaters.out.dynamoDb.entity.SatelliteEntity;
import com.mercadolibre.quasar.currego.infrastructure.adapaters.out.dynamoDb.mapper.SatelliteRepositoryMapper;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SatelliteRepositoryDynamoDbAdapter implements SatelliteRepository {

//    private List<Satellite> localSatellites;
    private final DynamoDBMapper dynamoDBMapper;
    private final SatelliteRepositoryMapper satelliteRepositoryMapper;


//    @PostConstruct
//    void setupLocal(){
//        this.localSatellites = new ArrayList<>();
//
//        Satellite kenobi = Satellite.builder()
//                .name("kenobi")
//                .positions(new double[]{-500, -200})
//                .message(new String[] {"este", "", "", "mensaje", ""})
//                .build();
//        Satellite skyWalker = Satellite.builder()
//                .name("skywalker")
//                .positions(new double[]{100, -100})
//                .message(new String[] {"", "es", "", "", "secreto"})
//                .build();
//        Satellite sato = Satellite.builder().name("sato")
//                .positions(new double[]{500, 100})
//                .message(new String[] {"este", "", "un", "", ""})
//                .build();
//
//        this.localSatellites = Arrays.asList(kenobi, skyWalker, sato);
//    }

    @Override
    public List<Satellite> findAll() {
        List<SatelliteEntity> result =  dynamoDBMapper.scan(SatelliteEntity.class, new DynamoDBScanExpression());
//        return this.localSatellites;
        return satelliteRepositoryMapper.toSatelliteList(result);
    }


    @Override
    public Optional<Satellite> findByName(String name){
//        return this.localSatellites.stream().filter(satellite -> satellite.getName().equals(name)).findAny();
        return Optional.empty();
    }
}
