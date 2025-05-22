package com.ilpalazzo.service;

import com.ilpalazzo.model.entity.TableInformation;

import java.util.List;

public interface TableInformationService {

    TableInformation createTableInformation(TableInformation tableInformation);

    List<TableInformation> getAllTableInformation();

    TableInformation getTableInformationById(String tableId);

    TableInformation getTableInformationByTableName(String tableName);

    void deleteTableInformation(String tableId);

    TableInformation updateTableInformation(String tableId, TableInformation updatedTableInformation);

}
