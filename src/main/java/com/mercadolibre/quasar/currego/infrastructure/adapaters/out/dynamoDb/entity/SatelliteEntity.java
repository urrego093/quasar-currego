package com.mercadolibre.quasar.currego.infrastructure.adapaters.out.dynamoDb.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Data;

import java.util.List;

@DynamoDBTable(tableName = "meli-quasar")
@Data
public class SatelliteEntity {
    @DynamoDBHashKey(attributeName = "satelliteName ")
    String satelliteName;
    @DynamoDBAttribute
    List<String> message;
    @DynamoDBAttribute
    List<Double> positions;
}
