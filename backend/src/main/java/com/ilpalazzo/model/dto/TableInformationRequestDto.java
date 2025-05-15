package com.ilpalazzo.model.dto;

import java.util.UUID;

public class TableInformationRequestDto {

    private String tableName;
    private String qrCodeUrl;

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
