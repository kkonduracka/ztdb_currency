package com.pk.backend.nosql.model;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;
import java.util.UUID;

import static org.springframework.data.elasticsearch.annotations.FieldType.Nested;

@org.springframework.data.mongodb.core.mapping.Document(collection = "data")
@Document(indexName = "data")
@Data
public class NosqlData {

    @Id
    @MongoId
    private UUID id;

    @Field("symbol")
    @org.springframework.data.mongodb.core.mapping.Field("symbol")
    private String symbol;

    @Field("name")
    @org.springframework.data.mongodb.core.mapping.Field("name")
    private String name;

    @Field("lastPrice")
    @org.springframework.data.mongodb.core.mapping.Field("lastPrice")
    private String lastPrice;

    @Field("change")
    @org.springframework.data.mongodb.core.mapping.Field("change")
    private String change;

    @Field("changePercent")
    @org.springframework.data.mongodb.core.mapping.Field("changePercent")
    private String changePercent;

    @Field(value = "currencyModel", type = Nested)
    @org.springframework.data.mongodb.core.mapping.Field(value = "currencyModel")
    private List<CurrencyModel> currencyModel;

}