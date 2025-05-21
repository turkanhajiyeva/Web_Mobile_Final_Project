package com.ilpalazzo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilpalazzo.ilpalazzoApplication;
import com.ilpalazzo.model.entity.MenuItem;
import com.ilpalazzo.repository.MenuItemRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = ilpalazzoApplication.class)
@AutoConfigureMockMvc
class MenuItemControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void cleanUp() {
        menuItemRepository.deleteAll();
    }

    @Test
    void createMenuItem_shouldReturnCreatedItem() throws Exception {
        MenuItem menuItem = new MenuItem();
        menuItem.setName("Pizza");
        menuItem.setDescription("Delicious cheese pizza");
        menuItem.setPrice(new BigDecimal("9.99"));
        menuItem.setCategory("Main Courses");

        mockMvc.perform(post("/api/menuitems")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(menuItem)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Pizza"))
                .andExpect(jsonPath("$.price").value(9.99))
                .andExpect(jsonPath("$.category").value("Main Courses"));
    }

    @Test
    void getAllMenuItems_shouldReturnList() throws Exception {
        MenuItem m1 = new MenuItem();
        m1.setName("Spaghetti");
        m1.setDescription("With tomato sauce");
        m1.setPrice(new BigDecimal("8.50"));
        m1.setCategory("Main Courses");

        MenuItem m2 = new MenuItem();
        m2.setName("Tiramisu");
        m2.setDescription("Classic dessert");
        m2.setPrice(new BigDecimal("4.99"));
        m2.setCategory("Appetizers");

        menuItemRepository.save(m1);
        menuItemRepository.save(m2);

        mockMvc.perform(get("/api/menuitems"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getMenuItemById_shouldReturnItem() throws Exception {
        MenuItem item = new MenuItem();
        item.setName("Lasagna");
        item.setDescription("Beef lasagna");
        item.setPrice(new BigDecimal("10.00"));
        item.setCategory("Main Courses");

        MenuItem saved = menuItemRepository.save(item);

        mockMvc.perform(get("/api/menuitems/" + saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Lasagna"))
                .andExpect(jsonPath("$.price").value(10.00));
    }

    @Test
    void updateMenuItem_shouldUpdate() throws Exception {
        MenuItem original = new MenuItem();
        original.setName("Risotto");
        original.setDescription("With mushrooms");
        original.setPrice(new BigDecimal("11.00"));
        original.setCategory("Main Courses");

        MenuItem saved = menuItemRepository.save(original);

        MenuItem updated = new MenuItem();
        updated.setId(saved.getId());
        updated.setName("Risotto Milanese");
        updated.setDescription("Saffron risotto");
        updated.setPrice(new BigDecimal("12.50"));
        updated.setCategory("Main Courses");

        mockMvc.perform(put("/api/menuitems/" + saved.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Risotto Milanese"))
                .andExpect(jsonPath("$.price").value(12.50));
    }

    @Test
    void deleteMenuItem_shouldDelete() throws Exception {
        MenuItem item = new MenuItem();
        item.setName("Panna Cotta");
        item.setDescription("Creamy dessert");
        item.setPrice(new BigDecimal("5.50"));
        item.setCategory("Appetizers");

        MenuItem saved = menuItemRepository.save(item);

        mockMvc.perform(delete("/api/menuitems/" + saved.getId()))
                .andExpect(status().isNoContent());
    }
}
