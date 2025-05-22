package com.ilpalazzo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilpalazzo.ilpalazzoApplication;
import com.ilpalazzo.model.dto.OrderItemRequestDto;
import com.ilpalazzo.model.dto.OrderRequestDto;
import com.ilpalazzo.model.entity.MenuItem;
import com.ilpalazzo.model.entity.Order;
import com.ilpalazzo.repository.MenuItemRepository;
import com.ilpalazzo.repository.OrderRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = ilpalazzoApplication.class)
@Import(OrderControllerIntegrationTest.TestSecurityConfig.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@ComponentScan(
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = com.ilpalazzo.security.SecurityConfig.class)
)
class OrderControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @BeforeEach
    void cleanUp() {
        orderRepository.deleteAll();
        menuItemRepository.deleteAll();

        doNothing().when(rabbitTemplate).convertAndSend(
            any(String.class),
            any(String.class),
            any(Object.class)
        );
    }

    @WithMockUser(username = "testuser", roles = {"USER"})
    @Test
    void placeOrder_shouldReturnOrderResponseDto() throws Exception {
        MenuItem menuItem = new MenuItem();
        menuItem.setName("Margherita");
        menuItem.setDescription("Cheese pizza");
        menuItem.setPrice(new BigDecimal("10.00"));
        menuItem.setCategory("Main Course");
        MenuItem savedItem = menuItemRepository.save(menuItem);

        OrderItemRequestDto orderItem = new OrderItemRequestDto();
        orderItem.setMenuItemId(savedItem.getId());
        orderItem.setQuantity(2);

        OrderRequestDto request = new OrderRequestDto();
        request.setTableId(UUID.randomUUID().toString());
        request.setUserId(UUID.randomUUID().toString());
        request.setItems(List.of(orderItem));

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tableId").value(request.getTableId()))
                .andExpect(jsonPath("$.userId").value(request.getUserId()))
                .andExpect(jsonPath("$.items[0].menuItemId").value(savedItem.getId()))
                .andExpect(jsonPath("$.items[0].quantity").value(2));
    }

    @WithMockUser(username = "testuser", roles = {"USER"})
    @Test
    void getAllOrders_shouldReturnEmptyInitially() throws Exception {
        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @WithMockUser(username = "testuser", roles = {"USER"})
    @Test
    void deleteOrder_shouldRemoveOrder() throws Exception {
        Order order = new Order();
        order.setTableId("test-table");
        order.setUserId("user-123");
        order.setStatus("pending");
        Order saved = orderRepository.save(order);

        mockMvc.perform(delete("/api/orders/" + saved.getOrderId()))
                .andExpect(status().isNoContent());

        assertThat(orderRepository.findById(saved.getOrderId())).isEmpty();
    }

    @WithMockUser(username = "testuser", roles = {"USER"})
    @Test
    void updateOrderStatus_shouldModifyStatus() throws Exception {
        Order order = new Order();
        order.setTableId("test-table");
        order.setUserId("user-123");
        order.setStatus("pending");
        Order saved = orderRepository.save(order);

        mockMvc.perform(put("/api/orders/" + saved.getOrderId() + "/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\": \"completed\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("completed"));
    }

    @WithMockUser(username = "testuser", roles = {"USER"})
    @Test
    void getOrderById_shouldReturnOrder() throws Exception {
        Order order = new Order();
        order.setTableId("sample-table");
        order.setUserId("user-abc");
        order.setStatus("pending");
        Order saved = orderRepository.save(order);

        mockMvc.perform(get("/api/orders/" + saved.getOrderId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tableId").value("sample-table"))
                .andExpect(jsonPath("$.userId").value("user-abc"));
    }

    @WithMockUser(username = "testuser", roles = {"USER"})
    @Test
    void getOrdersByStatus_shouldReturnMatchingOrders() throws Exception {
        Order order = new Order();
        order.setTableId("table-xyz");
        order.setUserId("user-xyz");
        order.setStatus("processing");
        orderRepository.save(order);

        mockMvc.perform(get("/api/orders/status/processing"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Configuration
    static class TestSecurityConfig {

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                .csrf().disable()
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
            return http.build();
        }
    }
}
