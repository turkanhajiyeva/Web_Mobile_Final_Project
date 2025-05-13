package com.ilpalazzo.mapper;

import com.ilpalazzo.model.dto.*;
import com.ilpalazzo.model.entity.*;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    public static Order toEntity(OrderRequestDto dto) {
        Order order = new Order();
        order.setTableId(dto.getTableId());
        order.setTotalAmount(dto.getTotalAmount());

        if (dto.getItems() != null) {
            List<OrderItem> items = dto.getItems().stream()
                    .map(OrderMapper::toEntity)
                    .collect(Collectors.toList());
            order.setItems(items);
        }

        return order;
    }

    public static OrderItem toEntity(OrderItemRequestDto dto) {
        OrderItem item = new OrderItem();
        item.setMenuItemId(dto.getMenuItemId());
        item.setQuantity(dto.getQuantity());
        return item;
    }

    public static OrderResponseDto toResponse(Order order) {
        OrderResponseDto dto = new OrderResponseDto();
        dto.setOrderId(order.getOrderId());
        dto.setTableId(order.getTableId());
        dto.setOrderTime(order.getOrderTime());
        dto.setStatus(order.getStatus());
        dto.setTotalAmount(order.getTotalAmount());

        if (order.getItems() != null) {
            List<OrderItemResponseDto> items = order.getItems().stream()
                    .map(OrderMapper::toResponse)
                    .collect(Collectors.toList());
            dto.setItems(items);
        }

        return dto;
    }

    public static OrderItemResponseDto toResponse(OrderItem item) {
        OrderItemResponseDto dto = new OrderItemResponseDto();
        dto.setOrderItemId(item.getOrderItemId());
        dto.setMenuItemId(item.getMenuItemId());
        dto.setQuantity(item.getQuantity());
        return dto;
    }
}
