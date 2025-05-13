package com.ilpalazzo.model.dto;

import java.util.UUID;

public class OrderItemRequestDto {

    private UUID menuItemId;
    private int quantity;

    public UUID getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(UUID menuItemId) {
        this.menuItemId = menuItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
