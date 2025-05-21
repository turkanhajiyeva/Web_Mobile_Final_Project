package com.ilpalazzo.model.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "table_information")
public class TableInformation {

    @Id
    @Column(name = "table_id", columnDefinition = "String")
    private UUID tableId;

    @Column(name = "table_name", length = 50, nullable = false)
    private String tableName;

    @Column(name = "qr_code_url", length = 255)
    private String qrCodeUrl;

    public UUID getTableId() {
        return tableId;
    }

    public void setTableId(UUID tableId) {
        this.tableId = tableId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }
}
