package com.pk.backend.nosql.repo.mongo;

import com.pk.backend.nosql.model.CurrencyModel;
import com.pk.backend.nosql.model.NosqlData;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MongoRepo extends org.springframework.data.mongodb.repository.MongoRepository<com.pk.backend.nosql.model.NosqlData, java.util.UUID>{

    public NosqlData findByName(String name);

    public NosqlData findByNameAndCurrencyModelDateBefore(String currency, LocalDate endDate);

    public NosqlData findByNameAndCurrencyModelDateAfter(String currency, LocalDate startDate);

    public NosqlData findByNameAndCurrencyModelDateBetween(String currency, LocalDate startDate, LocalDate endDate);


}
