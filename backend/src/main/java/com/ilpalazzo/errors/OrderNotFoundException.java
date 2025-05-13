package com.ilpalazzo.errors;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(Integer id) {
        super("Order not found with id: " + id);
    }
}