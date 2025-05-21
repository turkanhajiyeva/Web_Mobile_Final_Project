package com.ilpalazzo.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

import com.ilpalazzo.errors.MenuItemNotFoundException;
import com.ilpalazzo.model.dto.MenuItemRequestDto;
import com.ilpalazzo.model.dto.MenuItemResponseDto;
import com.ilpalazzo.model.entity.MenuItem;
import com.ilpalazzo.repository.MenuItemRepository;

class MenuItemServiceImplTest {

    @Mock
    private MenuItemRepository menuItemRepository;

    @InjectMocks
    private MenuItemServiceImpl menuItemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private MenuItem createMenuItemEntity(Integer id, String name, String desc, BigDecimal price, String category) {
        MenuItem item = new MenuItem();
        item.setId(id);
        item.setName(name);
        item.setDescription(desc);
        item.setPrice(price);
        item.setCategory(category);
        return item;
    }

    private MenuItemRequestDto createMenuItemRequestDto(String name, String desc, BigDecimal price, String category) {
        MenuItemRequestDto dto = new MenuItemRequestDto();
        dto.setName(name);
        dto.setDescription(desc);
        dto.setPrice(price);
        dto.setCategory(category);
        return dto;
    }

    @Test
    void createMenuItem_shouldReturnCreatedMenuItemResponse() {
        MenuItemRequestDto dto = createMenuItemRequestDto("Pizza", "Delicious cheese pizza", new BigDecimal("9.99"), "Food");
        MenuItem savedEntity = createMenuItemEntity(1, "Pizza", "Delicious cheese pizza", new BigDecimal("9.99"), "Food");

        when(menuItemRepository.save(any(MenuItem.class))).thenReturn(savedEntity);

        MenuItemResponseDto response = menuItemService.createMenuItem(dto);

        assertNotNull(response);
        assertEquals(1, response.getId());
        assertEquals("Pizza", response.getName());
        verify(menuItemRepository, times(1)).save(any(MenuItem.class));
    }

    @Test
    void getAllMenuItems_shouldReturnListOfMenuItemResponses() {
        List<MenuItem> list = List.of(
            createMenuItemEntity(1, "Pizza", "Delicious cheese pizza", new BigDecimal("9.99"), "Food"),
            createMenuItemEntity(2, "Coke", "Refreshing soda", new BigDecimal("1.99"), "Drink")
        );
        when(menuItemRepository.findAll()).thenReturn(list);

        List<MenuItemResponseDto> responseList = menuItemService.getAllMenuItems();

        assertEquals(2, responseList.size());
        verify(menuItemRepository, times(1)).findAll();
    }

    @Test
    void getMenuItemById_shouldReturnMenuItemResponse() {
        MenuItem entity = createMenuItemEntity(1, "Pizza", "Delicious cheese pizza", new BigDecimal("9.99"), "Food");
        when(menuItemRepository.findById(1)).thenReturn(Optional.of(entity));

        MenuItemResponseDto response = menuItemService.getMenuItemById(1);

        assertNotNull(response);
        assertEquals("Pizza", response.getName());
    }

    @Test
    void getMenuItemById_whenNotFound_shouldThrowException() {
        when(menuItemRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(MenuItemNotFoundException.class, () -> menuItemService.getMenuItemById(1));
    }

    @Test
    void getMenuItemByCategory_shouldReturnMenuItemsOfCategory() {
        List<MenuItem> list = List.of(
            createMenuItemEntity(1, "Pizza", "Delicious cheese pizza", new BigDecimal("9.99"), "Food")
        );
        when(menuItemRepository.findByCategory("Food")).thenReturn(list);

        List<MenuItemResponseDto> responseList = menuItemService.getMenuItemByCategory("Food");

        assertEquals(1, responseList.size());
        assertEquals("Food", responseList.get(0).getCategory());
    }

    @Test
    void updateMenuItem_shouldReturnUpdatedMenuItemResponse() {
        MenuItem existing = createMenuItemEntity(1, "Pizza", "Delicious cheese pizza", new BigDecimal("9.99"), "Food");
        MenuItemRequestDto updateDto = createMenuItemRequestDto("Veggie Pizza", "Healthy veggie pizza", new BigDecimal("10.99"), "Food");
        MenuItem updatedEntity = createMenuItemEntity(1, "Veggie Pizza", "Healthy veggie pizza", new BigDecimal("10.99"), "Food");

        when(menuItemRepository.findById(1)).thenReturn(Optional.of(existing));
        when(menuItemRepository.save(any(MenuItem.class))).thenReturn(updatedEntity);

        MenuItemResponseDto response = menuItemService.updateMenuItem(1, updateDto);

        assertEquals("Veggie Pizza", response.getName());
        assertEquals("Healthy veggie pizza", response.getDescription());
        assertEquals(new BigDecimal("10.99"), response.getPrice());
    }

    @Test
    void deleteMenuItem_shouldCallDeleteById() {
        when(menuItemRepository.existsById(1)).thenReturn(true);

        menuItemService.deleteMenuItem(1);

        verify(menuItemRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteMenuItem_whenNotFound_shouldThrowException() {
        when(menuItemRepository.existsById(1)).thenReturn(false);

        assertThrows(MenuItemNotFoundException.class, () -> menuItemService.deleteMenuItem(1));
    }
}
