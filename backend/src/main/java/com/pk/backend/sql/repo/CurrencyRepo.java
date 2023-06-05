package com.pk.backend.sql.repo;

import com.pk.backend.sql.model.CurrencyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface CurrencyRepo extends JpaRepository<CurrencyModel, UUID>{

    public List<CurrencyModel> findAllBySqlDataId(UUID id);
    public List<CurrencyModel> findAllBySqlDataIdAndDateBefore(UUID id, LocalDate date);
    public List<CurrencyModel> findAllBySqlDataIdAndDateAfter(UUID id, LocalDate date);
    public List<CurrencyModel> findAllBySqlDataIdAndDateBetween(UUID id, LocalDate date1, LocalDate date2);
}
