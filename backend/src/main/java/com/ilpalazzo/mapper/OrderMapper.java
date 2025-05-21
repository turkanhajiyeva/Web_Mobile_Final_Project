package com.ilpalazzo.mapper;

import com.ilpalazzo.model.dto.*;
import com.ilpalazzo.model.entity.*;
import com.ilpalazzo.service.MenuItemService;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    public static Order toEntity(OrderRequestDto dto) {
        Order order = new Order();
        order.setTableId(dto.getTableId());
        order.setTotalAmount(dto.getTotalAmount());

        if (dto.getItems() != null) {
            List<OrderItem> orderItems = dto.getItems().stream()
                    .map(itemDto -> toEntity(itemDto, order))
                    .collect(Collectors.toList());
            order.setItems(orderItems);
        }

        return order;
    }

    public static OrderItem toEntity(OrderItemRequestDto dto) {
        OrderItem orderItem = new OrderItem();
        orderItem.setMenuItemId(dto.getMenuItemId());
        orderItem.setQuantity(dto.getQuantity());
        return orderItem;
    }

    public static OrderItem toEntity(OrderItemRequestDto dto, Order order) {
        OrderItem orderItem = toEntity(dto);
        orderItem.setOrder(order);
        return orderItem;
    }

    public static List<OrderItem> mapOrderItems(List<OrderItemRequestDto> itemsDto, Order order) {
        if (itemsDto == null) {
            return null;
        }
        return itemsDto.stream()
                .map(dto -> toEntity(dto, order))
                .collect(Collectors.toList());
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

    public static OrderResponseDto toResponse(Order order, MenuItemService menuItemService) {
        OrderResponseDto dto = new OrderResponseDto();
        dto.setOrderId(order.getOrderId());
        dto.setTableId(order.getTableId());
        dto.setOrderTime(order.getOrderTime());
        dto.setStatus(order.getStatus());
        dto.setTotalAmount(order.getTotalAmount());
        if (order.getItems() != null) {
            List<OrderItemResponseDto> items = order.getItems().stream().map(orderItem -> {
                OrderItemResponseDto itemDto = toResponse(orderItem);
                try {
                    MenuItemResponseDto menuItemDto = menuItemService.getMenuItemById((long) orderItem.getMenuItemId());
                    itemDto.setMenuItem(menuItemDto);
                } catch (Exception e) {
                    itemDto.setMenuItem(null);
                }
                return itemDto;
            }).collect(Collectors.toList());
            dto.setItems(items);
        }
        return dto;
    }

    public static OrderItemResponseDto toResponse(OrderItem orderItem) {
        OrderItemResponseDto dto = new OrderItemResponseDto();
        dto.setOrderItemId(orderItem.getOrderItemId());
        dto.setMenuItemId(orderItem.getMenuItemId());
        dto.setQuantity(orderItem.getQuantity());
        return dto;
    }
}
