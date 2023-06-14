package com.pk.backend.sql.repo;

import com.pk.backend.sql.model.CurrencyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface CurrencyRepo extends JpaRepository<CurrencyModel, UUID>{

    @Transactional
    @Query(value = "SELECT * FROM currency_model WHERE data_id = ?1 ORDER BY date DESC", nativeQuery = true)
    List<CurrencyModel> findAllBySqlDataId(UUID id);
    @Transactional
    List<CurrencyModel> findAllBySqlDataIdAndDateBefore(UUID id, LocalDate date);
    @Transactional
    List<CurrencyModel> findAllBySqlDataIdAndDateAfter(UUID id, LocalDate date);
    @Transactional
    List<CurrencyModel> findAllBySqlDataIdAndDateBetween(UUID id, LocalDate date1, LocalDate date2);
}
