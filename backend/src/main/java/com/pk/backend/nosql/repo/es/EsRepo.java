package com.pk.backend.nosql.repo.es;

import com.pk.backend.nosql.model.NosqlData;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EsRepo extends ElasticsearchRepository<NosqlData, UUID> {
}
