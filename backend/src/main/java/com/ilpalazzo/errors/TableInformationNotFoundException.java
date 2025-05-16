package com.ilpalazzo.errors;

import java.util.UUID;

public class TableInformationNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TableInformationNotFoundException(UUID tableId) {
        super("Table information not found with id: " + tableId);
    }

    public TableInformationNotFoundException(String tableName) {
        super("Table information not found with table name: " + tableName);
    }

    public TableInformationNotFoundException(String message, boolean isCustomMessage) {
        super(message);
    }
}
