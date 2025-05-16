package com.ilpalazzo.service.impl;

import com.ilpalazzo.errors.MenuItemNotFoundException;
import com.ilpalazzo.mapper.MenuItemMapper;
import com.ilpalazzo.model.dto.MenuItemRequestDto;
import com.ilpalazzo.model.dto.MenuItemResponseDto;
import com.ilpalazzo.model.entity.MenuItem;
import com.ilpalazzo.repository.MenuItemRepository;
import com.ilpalazzo.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Override
    public MenuItemResponseDto createMenuItem(MenuItemRequestDto dto) {
        MenuItem item = MenuItemMapper.toEntity(dto);
        return MenuItemMapper.toResponse(menuItemRepository.save(item));
    }

    @Override
    public List<MenuItemResponseDto> getAllMenuItems() {
        return menuItemRepository.findAll()
                .stream()
                .map(MenuItemMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public MenuItemResponseDto getMenuItemById(Long id) {
        MenuItem item = menuItemRepository.findById(id)
                .orElseThrow(() -> new MenuItemNotFoundException(id));
        return MenuItemMapper.toResponse(item);
    }

    @Override
    public List<MenuItemResponseDto> getMenuItemByCategory(String category) {
        List<MenuItem> items = menuItemRepository.findByCategory(category);
        if (items.isEmpty()) {
            throw new MenuItemNotFoundException(category);
        }
        return items.stream()
                .map(MenuItemMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public MenuItemResponseDto updateMenuItem(Long id, MenuItemRequestDto dto) {
        MenuItem existing = menuItemRepository.findById(id)
                .orElseThrow(() -> new MenuItemNotFoundException(id));

        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());
        existing.setPrice(dto.getPrice());
        existing.setCategory(dto.getCategory());

        return MenuItemMapper.toResponse(menuItemRepository.save(existing));
    }

    @Override
    public void deleteMenuItem(Long id) {
        if (!menuItemRepository.existsById(id)) {
            throw new MenuItemNotFoundException(id);
        }
        menuItemRepository.deleteById(id);
    }
}
