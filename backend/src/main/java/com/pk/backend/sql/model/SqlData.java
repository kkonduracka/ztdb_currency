package com.pk.backend.sql.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Entity(name = "data")
@Data
public class SqlData {

    @Id
    private UUID id;

    @Column(name = "symbol")
    private String symbol;
    @Column(name = "name")
    private String name;
    @Column(name = "lastPrice")
    private String lastPrice;
    @Column(name = "change")
    private String change;
    @Column(name = "changePercent")
    private String changePercent;

}
