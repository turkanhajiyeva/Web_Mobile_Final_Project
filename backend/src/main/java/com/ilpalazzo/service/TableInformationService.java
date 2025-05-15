package com.ilpalazzo.service;

import com.ilpalazzo.model.entity.TableInformation;

import java.util.List;
import java.util.UUID;

public interface TableInformationService {

    TableInformation createTableInformation(TableInformation tableInformation);

    List<TableInformation> getAllTableInformation();

    TableInformation getTableInformationById(UUID tableId);

    void deleteTableInformation(UUID tableId);

    TableInformation updateTableInformation(UUID tableId, TableInformation updatedTableInformation);

}
