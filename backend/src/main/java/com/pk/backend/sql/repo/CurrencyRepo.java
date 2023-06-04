package com.pk.backend.sql.repo;

import com.pk.backend.sql.model.CurrencyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CurrencyRepo extends JpaRepository<CurrencyModel, UUID>{
}
