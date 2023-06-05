package com.pk.backend.nosql.model;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;

@Data
public class CurrencyModel {
    @Field(name = "date", type = FieldType.Date)
    private LocalDate date;
    private String open;
    private String high;
    private String low;
    private String close;
    private String adjClose;
}
