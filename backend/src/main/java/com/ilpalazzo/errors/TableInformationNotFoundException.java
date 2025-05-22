package com.ilpalazzo.errors;

public class TableInformationNotFoundException extends RuntimeException {

    public TableInformationNotFoundException(String tableId) {
        super("Table information not found with id: " + tableId);
    }

    public TableInformationNotFoundException(String tableName, boolean isByName) {
        super("Table information not found with table name: " + tableName);
    }
}
