package com.ilpalazzo.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.ilpalazzo.errors.TableInformationNotFoundException;
import com.ilpalazzo.model.entity.TableInformation;
import com.ilpalazzo.repository.TableInformationRepository;

class TableInformationServiceImplTest {

    @Mock
    private TableInformationRepository tableInformationRepository;

    @InjectMocks
    private TableInformationServiceImpl tableInformationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTableInformation_shouldReturnSavedEntity() {
        UUID id = UUID.randomUUID();
        TableInformation table = new TableInformation();
        table.setTableId(id);
        table.setTableName("A1");
        table.setQrCodeUrl("http://qr.com/a1");

        when(tableInformationRepository.save(table)).thenReturn(table);

        TableInformation result = tableInformationService.createTableInformation(table);

        assertNotNull(result);
        assertEquals("A1", result.getTableName());
        verify(tableInformationRepository).save(table);
    }

    @Test
    void getAllTableInformation_shouldReturnList() {
        TableInformation t1 = new TableInformation();
        t1.setTableId(UUID.randomUUID());
        t1.setTableName("A1");

        TableInformation t2 = new TableInformation();
        t2.setTableId(UUID.randomUUID());
        t2.setTableName("B2");

        when(tableInformationRepository.findAll()).thenReturn(List.of(t1, t2));

        List<TableInformation> result = tableInformationService.getAllTableInformation();

        assertEquals(2, result.size());
        verify(tableInformationRepository).findAll();
    }

    @Test
    void getTableInformationById_shouldReturnEntity() {
        UUID id = UUID.randomUUID();
        TableInformation table = new TableInformation();
        table.setTableId(id);
        table.setTableName("C3");

        when(tableInformationRepository.findById(id)).thenReturn(Optional.of(table));

        TableInformation result = tableInformationService.getTableInformationById(id);

        assertNotNull(result);
        assertEquals("C3", result.getTableName());
    }

    @Test
    void getTableInformationById_whenNotFound_shouldThrowException() {
        UUID id = UUID.randomUUID();
        when(tableInformationRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(TableInformationNotFoundException.class,
                     () -> tableInformationService.getTableInformationById(id));
    }

    @Test
    void getTableInformationByTableName_shouldReturnEntity() {
        TableInformation table = new TableInformation();
        table.setTableId(UUID.randomUUID());
        table.setTableName("VIP");

        when(tableInformationRepository.findByTableName("VIP")).thenReturn(Optional.of(table));

        TableInformation result = tableInformationService.getTableInformationByTableName("VIP");

        assertEquals("VIP", result.getTableName());
    }

    @Test
    void getTableInformationByTableName_whenNotFound_shouldThrowException() {
        when(tableInformationRepository.findByTableName("X")).thenReturn(Optional.empty());

        assertThrows(TableInformationNotFoundException.class,
                     () -> tableInformationService.getTableInformationByTableName("X"));
    }

    @Test
    void deleteTableInformation_shouldCallDelete() {
        UUID id = UUID.randomUUID();
        when(tableInformationRepository.existsById(id)).thenReturn(true);

        tableInformationService.deleteTableInformation(id);

        verify(tableInformationRepository).deleteById(id);
    }

    @Test
    void deleteTableInformation_whenNotFound_shouldThrowException() {
        UUID id = UUID.randomUUID();
        when(tableInformationRepository.existsById(id)).thenReturn(false);

        assertThrows(TableInformationNotFoundException.class,
                     () -> tableInformationService.deleteTableInformation(id));
    }

    @Test
    void updateTableInformation_shouldUpdateAndReturnEntity() {
        UUID id = UUID.randomUUID();

        TableInformation existing = new TableInformation();
        existing.setTableId(id);
        existing.setTableName("Old");
        existing.setQrCodeUrl("http://old.com");

        TableInformation updated = new TableInformation();
        updated.setTableId(id);
        updated.setTableName("New");
        updated.setQrCodeUrl("http://new.com");

        when(tableInformationRepository.findById(id)).thenReturn(Optional.of(existing));
        when(tableInformationRepository.save(any(TableInformation.class))).thenReturn(updated);

        TableInformation result = tableInformationService.updateTableInformation(id, updated);

        assertEquals("New", result.getTableName());
        assertEquals("http://new.com", result.getQrCodeUrl());
    }
}
