package com.mercadolibre.quasar.currego.infrastructure.adapaters.out.dynamoDb.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@DynamoDBTable(tableName = "meli-quasar")
@Data
@NoArgsConstructor
public class SatelliteEntity {
    @DynamoDBHashKey(attributeName = "satelliteName")
    String satelliteName;
    @DynamoDBAttribute(attributeName = "message")
    List<String> message;
    @DynamoDBAttribute(attributeName = "positions")
    List<Double> positions;

    @DynamoDBAttribute(attributeName = "distance")
    Double distance;
}
