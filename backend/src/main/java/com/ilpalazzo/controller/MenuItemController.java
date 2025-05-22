package com.ilpalazzo.controller;

import com.ilpalazzo.model.dto.MenuItemRequestDto;
import com.ilpalazzo.model.dto.MenuItemResponseDto;
import com.ilpalazzo.service.MenuItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menuitems")
public class MenuItemController {

    @Autowired
    private MenuItemService menuItemService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<MenuItemResponseDto> createMenuItem(@RequestBody MenuItemRequestDto menuItemDto) {
        MenuItemResponseDto created = menuItemService.createMenuItem(menuItemDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<MenuItemResponseDto>> getAllMenuItems() {
        List<MenuItemResponseDto> list = menuItemService.getAllMenuItems();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItemResponseDto> getMenuItemById(@PathVariable int id) {
        MenuItemResponseDto item = menuItemService.getMenuItemById(id);
        return ResponseEntity.ok(item);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<MenuItemResponseDto>> getMenuItemsByCategory(@PathVariable String category) {
        List<MenuItemResponseDto> items = menuItemService.getMenuItemByCategory(category);
        return ResponseEntity.ok(items);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable int id) {
        menuItemService.deleteMenuItem(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<MenuItemResponseDto> updateMenuItem(
            @PathVariable int id,
            @RequestBody MenuItemRequestDto updatedMenuItem) {

        MenuItemResponseDto updated = menuItemService.updateMenuItem(id, updatedMenuItem);
        return ResponseEntity.ok(updated);
    }
}
