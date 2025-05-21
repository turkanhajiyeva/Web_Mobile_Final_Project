package com.ilpalazzo.model.dto;

import java.util.UUID;

public class OrderItemResponseDto {

    private Long orderItemId;
    private int menuItemId;
    private int quantity;
    private MenuItemResponseDto menuItem;

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public int getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(int menuItemId) {
        this.menuItemId = menuItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public MenuItemResponseDto getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItemResponseDto menuItem) {
        this.menuItem = menuItem;
    }
}
