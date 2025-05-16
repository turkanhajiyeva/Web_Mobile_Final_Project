package com.ilpalazzo.service;

import com.ilpalazzo.model.dto.MenuItemRequestDto;
import com.ilpalazzo.model.dto.MenuItemResponseDto;

import java.util.List;

public interface MenuItemService {
    MenuItemResponseDto createMenuItem(MenuItemRequestDto dto);
    List<MenuItemResponseDto> getAllMenuItems();
    MenuItemResponseDto getMenuItemById(Long id);
    List<MenuItemResponseDto> getMenuItemByCategory(String category); 
    MenuItemResponseDto updateMenuItem(Long id, MenuItemRequestDto dto);
    void deleteMenuItem(Long id);
}
