package com.pk.backend.sql.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Entity(name = "currencyModel")
@Data
public class CurrencyModel {

    @Id
    private UUID id;

    private LocalDate date;
    private String open;
    private String high;
    private String low;
    private String close;
    private String adjClose;

    @ManyToOne
    @JoinColumn(name="data_id", nullable=false)
    private SqlData sqlData;

}
