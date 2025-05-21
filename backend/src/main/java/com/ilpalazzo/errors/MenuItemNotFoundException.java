package com.ilpalazzo.errors;

public class MenuItemNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public MenuItemNotFoundException(int id) {
        super("MenuItem not found with id: " + id);
    }
    
    public MenuItemNotFoundException(String category) {
        super("MenuItem not found with category: " + category);
    }

    public MenuItemNotFoundException(String message, boolean isCustomMessage) {
        super(message);
    }
}
