package com.ilpalazzo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilpalazzo.model.dto.MenuItemRequestDto;
import com.ilpalazzo.model.entity.MenuItem;
import com.ilpalazzo.repository.MenuItemRepository;
import com.ilpalazzo.ilpalazzoApplication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(classes = ilpalazzoApplication.class)
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class MenuItemControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void cleanDatabase() {
        menuItemRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createMenuItem_shouldReturnCreatedItem() throws Exception {
        MenuItemRequestDto request = new MenuItemRequestDto();
        request.setName("Pizza Margherita");
        request.setDescription("Classic Italian pizza");
        request.setPrice(new BigDecimal("12.99"));
        request.setCategory("Main Course");

        String response = mockMvc.perform(post("/api/menuitems")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.name").value("Pizza Margherita"))
            // Expect price as number
            .andExpect(jsonPath("$.price").value(12.99))
            .andExpect(jsonPath("$.category").value("Main Course"))
            .andReturn()
            .getResponse()
            .getContentAsString();

        System.out.println("Response JSON: " + response);
    }


    @Test
    @WithMockUser(roles = "USER")
    void getAllMenuItems_shouldReturnList() throws Exception {
        // Insert one item directly to repo
        MenuItem item = new MenuItem();
        item.setName("Spaghetti Carbonara");
        item.setDescription("Pasta with eggs, cheese, pancetta");
        item.setPrice(new BigDecimal("14.50"));
        item.setCategory("Main Course");
        menuItemRepository.save(item);

        mockMvc.perform(get("/api/menuitems")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$[0].name").value("Spaghetti Carbonara"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void getMenuItemById_shouldReturnItem() throws Exception {
        MenuItem item = new MenuItem();
        item.setName("Tiramisu");
        item.setDescription("Classic Italian dessert");
        item.setPrice(new BigDecimal("6.00"));
        item.setCategory("Dessert");
        item = menuItemRepository.save(item);

        mockMvc.perform(get("/api/menuitems/{id}", item.getId())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Tiramisu"))
            .andExpect(jsonPath("$.category").value("Dessert"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateMenuItem_shouldUpdate() throws Exception {
        MenuItem item = new MenuItem();
        item.setName("Old Name");
        item.setDescription("Old Description");
        item.setPrice(new BigDecimal("10.00"));
        item.setCategory("Old Category");
        item = menuItemRepository.save(item);

        MenuItemRequestDto updateRequest = new MenuItemRequestDto();
        updateRequest.setName("Updated Name");
        updateRequest.setDescription("Updated Description");
        updateRequest.setPrice(new BigDecimal("11.00"));
        updateRequest.setCategory("Updated Category");

        mockMvc.perform(put("/api/menuitems/{id}", item.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Updated Name"))
            .andExpect(jsonPath("$.price").value(11.00))
            .andExpect(jsonPath("$.category").value("Updated Category"));

        // Verify updated in DB
        MenuItem updated = menuItemRepository.findById(item.getId()).orElseThrow();
        assertThat(updated.getName()).isEqualTo("Updated Name");
        assertThat(updated.getPrice()).isEqualByComparingTo(new BigDecimal("11.00"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteMenuItem_shouldDelete() throws Exception {
        MenuItem item = new MenuItem();
        item.setName("To be deleted");
        item.setDescription("Description");
        item.setPrice(new BigDecimal("5.00"));
        item.setCategory("Category");
        item = menuItemRepository.save(item);

        mockMvc.perform(delete("/api/menuitems/{id}", item.getId()))
            .andExpect(status().isNoContent());

        assertThat(menuItemRepository.existsById(item.getId())).isFalse();
    }
}
