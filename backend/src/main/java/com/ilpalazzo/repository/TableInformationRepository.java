package com.ilpalazzo.repository;

import com.ilpalazzo.model.entity.TableInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface TableInformationRepository extends JpaRepository<TableInformation, UUID> {
    Optional<TableInformation> findByTableId(UUID tableId);
    List<TableInformation> findByTableName(String tableName);
}
