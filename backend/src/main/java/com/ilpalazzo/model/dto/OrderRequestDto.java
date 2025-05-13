package com.ilpalazzo.model.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class OrderRequestDto {

    private UUID tableId;
    private List<OrderItemRequestDto> items;
    private BigDecimal totalAmount;

    public UUID getTableId() {
        return tableId;
    }

    public void setTableId(UUID tableId) {
        this.tableId = tableId;
    }

    public List<OrderItemRequestDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemRequestDto> items) {
        this.items = items;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
