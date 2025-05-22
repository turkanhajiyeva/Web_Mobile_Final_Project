package com.ilpalazzo.repository;

import com.ilpalazzo.model.entity.TableInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TableInformationRepository extends JpaRepository<TableInformation, String> {
    Optional<TableInformation> findByTableId(String tableId);
    Optional<TableInformation> findByTableName(String tableName);
}
