package com.pk.backend.sql.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class CurrencyDTO {
    private UUID id;

    private LocalDate date;
    private String open;
    private String high;
    private String low;
    private String close;
    private String adjClose;
}
