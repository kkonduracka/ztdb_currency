package com.pk.backend.sql.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class DataDTO {

    private UUID id;

    private String symbol;
    private String name;
    private String lastPrice;
    private String change;
    private String changePercent;
    private List<CurrencyDTO> currencyModel;
    private String time;
}
