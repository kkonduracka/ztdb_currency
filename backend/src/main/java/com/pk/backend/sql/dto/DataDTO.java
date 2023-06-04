package com.pk.backend.sql.dto;

import lombok.Data;

@Data
public class DataDTO {

    private String symbol;
    private String name;
    private String lastPrice;
    private String change;
    private String changePercent;

}
