package com.pk.backend.sql.controller;

import com.pk.backend.sql.model.CurrencyModel;
import com.pk.backend.sql.model.SqlData;
import com.pk.backend.sql.repo.CurrencyRepo;
import com.pk.backend.sql.repo.DataRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/postgres")
@RequiredArgsConstructor
public class PostgresController {

    private final DataRepo dataRepo;
    private final CurrencyRepo currencyRepo;

    @GetMapping("/data")
    public List<SqlData> getData() {
        return dataRepo.findAll();
    }

    @PostMapping("/data")
    public SqlData postData(@RequestBody SqlData sqlData) {
        return dataRepo.save(sqlData);
    }

    @GetMapping("/currency")
    public List<CurrencyModel> getCurrency() {
        return currencyRepo.findAll();
    }

    @PostMapping("/currency")
    public CurrencyModel postCurrency(@RequestBody CurrencyModel currencyModel) {
        return currencyRepo.save(currencyModel);
    }
}
