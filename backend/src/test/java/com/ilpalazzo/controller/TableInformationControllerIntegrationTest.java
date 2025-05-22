package com.ilpalazzo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilpalazzo.ilpalazzoApplication;
import com.ilpalazzo.model.entity.TableInformation;
import com.ilpalazzo.repository.TableInformationRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = ilpalazzoApplication.class)
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class TableInformationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TableInformationRepository tableInformationRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void cleanUp() {
        tableInformationRepository.deleteAll();
    }

    @Test
    void createTableInformation_shouldReturnCreated() throws Exception {
        TableInformation table = new TableInformation();
        table.setTableId("table-123");
        table.setTableName("Test Table");
        table.setQrCodeUrl("http://example.com/qr");

        mockMvc.perform(post("/api/tableinformation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(table)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tableName").value("Test Table"))
                .andExpect(jsonPath("$.qrCodeUrl").value("http://example.com/qr"));
    }

    @Test
    void getAllTableInformation_shouldReturnList() throws Exception {
        TableInformation t1 = new TableInformation();
        t1.setTableId("table-1");
        t1.setTableName("Table A");
        t1.setQrCodeUrl("");

        TableInformation t2 = new TableInformation();
        t2.setTableId("table-2");
        t2.setTableName("Table B");
        t2.setQrCodeUrl("");

        tableInformationRepository.saveAll(List.of(t1, t2));

        mockMvc.perform(get("/api/tableinformation"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getTableInformationById_shouldReturnTable() throws Exception {
        TableInformation t = new TableInformation();
        String id = "table-123";
        t.setTableId(id);
        t.setTableName("Specific Table");
        t.setQrCodeUrl("http://qr.url");

        tableInformationRepository.save(t);

        mockMvc.perform(get("/api/tableinformation/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tableName").value("Specific Table"));
    }

    @Test
    void getTableInformationByTableName_shouldReturnTable() throws Exception {
        TableInformation t = new TableInformation();
        String id = "table-456";
        t.setTableId(id);
        t.setTableName("UniqueName");
        t.setQrCodeUrl("http://qr.url");

        tableInformationRepository.save(t);

        mockMvc.perform(get("/api/tableinformation/tableName/UniqueName"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tableId").value(id));
    }

    @Test
    void updateTableInformation_shouldUpdateSuccessfully() throws Exception {
        TableInformation t = new TableInformation();
        String id = "table-789";
        t.setTableId(id);
        t.setTableName("Old Name");
        t.setQrCodeUrl("");

        tableInformationRepository.save(t);

        TableInformation updated = new TableInformation();
        updated.setTableId(id);
        updated.setTableName("Updated Name");
        updated.setQrCodeUrl("http://updated.url");

        mockMvc.perform(put("/api/tableinformation/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tableName").value("Updated Name"))
                .andExpect(jsonPath("$.qrCodeUrl").value("http://updated.url"));
    }

    @Test
    void deleteTableInformation_shouldDeleteSuccessfully() throws Exception {
        TableInformation t = new TableInformation();
        String id = "table-999";
        t.setTableId(id);
        t.setTableName("To Be Deleted");
        t.setQrCodeUrl("");

        tableInformationRepository.save(t);

        mockMvc.perform(delete("/api/tableinformation/" + id))
                .andExpect(status().isNoContent());
    }
}
