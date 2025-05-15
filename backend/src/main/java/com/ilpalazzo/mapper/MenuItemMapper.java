package com.ilpalazzo.mapper;

import com.ilpalazzo.model.dto.MenuItemRequestDto;
import com.ilpalazzo.model.dto.MenuItemResponseDto;
import com.ilpalazzo.model.entity.MenuItem;

public class MenuItemMapper {

    public static MenuItem toEntity(MenuItemRequestDto dto) {
        MenuItem menuItem = new MenuItem();
        menuItem.setName(dto.getName());
        menuItem.setDescription(dto.getDescription());
        menuItem.setPrice(dto.getPrice());
        menuItem.setCategory(dto.getCategory());
        return menuItem;
    }

    public static MenuItemResponseDto toResponse(MenuItem menuItem) {
        MenuItemResponseDto dto = new MenuItemResponseDto();
        dto.setId(menuItem.getId());
        dto.setName(menuItem.getName());
        dto.setDescription(menuItem.getDescription());
        dto.setPrice(menuItem.getPrice());
        dto.setCategory(menuItem.getCategory());
        return dto;
    }
}
