package com.ilpalazzo.errors;

import java.util.UUID;

public class TableInformationNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TableInformationNotFoundException(UUID tableId) {
        super("Table information not found with id: " + tableId);
    }

    public TableInformationNotFoundException(String message) {
        super(message);
    }
}
