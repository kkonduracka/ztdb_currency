package com.pk.backend.nosql.repo.es;

import com.pk.backend.nosql.model.CurrencyModel;
import com.pk.backend.nosql.model.NosqlData;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.annotations.SourceFilters;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface EsRepo extends ElasticsearchRepository<NosqlData, UUID> {

    public NosqlData findByName(String name);

    public NosqlData findByNameAndCurrencyModelDateBefore(String currency, LocalDate endDate);

    public NosqlData findByNameAndCurrencyModelDateAfter(String currency, LocalDate startDate);

    public NosqlData findByNameAndCurrencyModelDateBetween(String currency, LocalDate startDate, LocalDate endDate);

}
