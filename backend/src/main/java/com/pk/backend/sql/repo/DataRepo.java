package com.pk.backend.sql.repo;

import com.pk.backend.sql.model.SqlData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DataRepo extends JpaRepository<SqlData, UUID> {
}
