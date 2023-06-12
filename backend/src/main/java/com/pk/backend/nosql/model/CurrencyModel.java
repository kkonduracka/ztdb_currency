package com.pk.backend.nosql.model;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;

@Data
public class CurrencyModel {
    @Field(name = "date", type = FieldType.Date)
    private LocalDate date;
    @Field(name = "open", type = FieldType.Double)
    private Double open;
    @Field(name = "high", type = FieldType.Double)
    private Double high;
    @Field(name = "low", type = FieldType.Double)
    private Double low;
    @Field(name = "close", type = FieldType.Double)
    private Double close;
    @Field(name = "adjClose", type = FieldType.Double)
    private Double adjClose;
}
