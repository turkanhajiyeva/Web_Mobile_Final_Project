package com.ilpalazzo.controller;

import com.ilpalazzo.model.entity.TableInformation;
import com.ilpalazzo.service.TableInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tableinformation")
public class TableInformationController {

    @Autowired
    private TableInformationService tableInformationService;

    @PostMapping
    public ResponseEntity<TableInformation> createTableInformation(@RequestBody TableInformation tableInformation) {
        TableInformation created = tableInformationService.createTableInformation(tableInformation);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<TableInformation>> getAllTableInformation() {
        return ResponseEntity.ok(tableInformationService.getAllTableInformation());
    }

    @GetMapping("/{tableId}")
    public ResponseEntity<TableInformation> getTableInformationById(@PathVariable UUID tableId) {
        return ResponseEntity.ok(tableInformationService.getTableInformationById(tableId));
    }

    @DeleteMapping("/{tableId}")
    public ResponseEntity<Void> deleteTableInformation(@PathVariable UUID tableId) {
        tableInformationService.deleteTableInformation(tableId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{tableId}")
    public ResponseEntity<TableInformation> updateTableInformation(
            @PathVariable UUID tableId,
            @RequestBody TableInformation updatedTableInformation) {
        TableInformation updated = tableInformationService.updateTableInformation(tableId, updatedTableInformation);
        return ResponseEntity.ok(updated);
    }
}
