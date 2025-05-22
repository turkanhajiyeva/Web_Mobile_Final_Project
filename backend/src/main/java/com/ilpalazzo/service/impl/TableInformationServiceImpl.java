package com.ilpalazzo.service.impl;

import com.ilpalazzo.errors.TableInformationNotFoundException;
import com.ilpalazzo.model.entity.TableInformation;
import com.ilpalazzo.repository.TableInformationRepository;
import com.ilpalazzo.service.TableInformationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableInformationServiceImpl implements TableInformationService {

    private static final Logger log = LoggerFactory.getLogger(TableInformationServiceImpl.class);

    @Autowired
    private TableInformationRepository tableInformationRepository;

    @Override
    public TableInformation createTableInformation(TableInformation tableInformation) {
        return tableInformationRepository.save(tableInformation);
    }

    @Override
    public List<TableInformation> getAllTableInformation() {
        return tableInformationRepository.findAll();
    }

    @Override
    public TableInformation getTableInformationById(String tableId) {
        return tableInformationRepository.findById(tableId)
                .orElseThrow(() -> new TableInformationNotFoundException(tableId));
    }

    @Override
    public TableInformation getTableInformationByTableName(String tableName) {
        return tableInformationRepository.findByTableName(tableName)
                .orElseThrow(() -> new TableInformationNotFoundException(tableName));
    }

    @Override
    public void deleteTableInformation(String tableId) {
        if (!tableInformationRepository.existsById(tableId)) {
            throw new TableInformationNotFoundException(tableId);
        }
        tableInformationRepository.deleteById(tableId);
    }

    @Override
    public TableInformation updateTableInformation(String tableId, TableInformation updatedTableInformation) {
        TableInformation existing = getTableInformationById(tableId);

        existing.setTableName(updatedTableInformation.getTableName());
        existing.setQrCodeUrl(updatedTableInformation.getQrCodeUrl());

        return tableInformationRepository.save(existing);
    }
}
