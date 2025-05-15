package com.ilpalazzo.errors;

public class MenuItemNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public MenuItemNotFoundException(Long id) {
        super("MenuItem not found with id: " + id);
    }

    public MenuItemNotFoundException(String message) {
        super(message);
    }
}
