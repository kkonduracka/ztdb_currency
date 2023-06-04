package com.pk.backend.nosql.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CurrencyModel {
    private LocalDate date;
    private String open;
    private String high;
    private String low;
    private String close;
    private String adjClose;
}
