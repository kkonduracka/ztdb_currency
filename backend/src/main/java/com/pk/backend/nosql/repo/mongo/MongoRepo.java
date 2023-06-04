package com.pk.backend.nosql.repo.mongo;

import org.springframework.stereotype.Repository;

@Repository
public interface MongoRepo extends org.springframework.data.mongodb.repository.MongoRepository<com.pk.backend.nosql.model.NosqlData, java.util.UUID>{
}
