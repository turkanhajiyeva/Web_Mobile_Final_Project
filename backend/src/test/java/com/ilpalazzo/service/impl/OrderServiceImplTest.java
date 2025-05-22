package com.ilpalazzo.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.ilpalazzo.errors.OrderNotFoundException;
import com.ilpalazzo.model.entity.Order;
import com.ilpalazzo.repository.OrderRepository;

class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order order1;
    private Order order2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        order1 = new Order();
        order1.setOrderId(1L);
        order1.setUserId("user-1234567890abcdef");   // <-- set userId here
        order1.setTableId(UUID.randomUUID().toString());
        order1.setOrderTime(LocalDateTime.now());
        order1.setTotalAmount(new BigDecimal("100.00"));
        order1.setStatus("pending");

        order2 = new Order();
        order2.setOrderId(2L);
        order2.setUserId("user-abcdef1234567890");   // <-- set userId here
        order2.setTableId(UUID.randomUUID().toString());
        order2.setOrderTime(LocalDateTime.now());
        order2.setTotalAmount(new BigDecimal("50.00"));
        order2.setStatus("ready");
    }

    @Test
    void placeOrder_shouldReturnSavedOrder() {
        when(orderRepository.save(order1)).thenReturn(order1);

        Order result = orderService.placeOrder(order1);

        assertNotNull(result);
        assertEquals(order1.getOrderId(), result.getOrderId());
        assertEquals(order1.getUserId(), result.getUserId());  // verify userId
        verify(orderRepository, times(1)).save(order1);
    }

    @Test
    void getAllOrders_shouldReturnAllOrders() {
        List<Order> orders = List.of(order1, order2);
        when(orderRepository.findAll()).thenReturn(orders);

        List<Order> result = orderService.getAllOrders();

        assertEquals(2, result.size());
        // verify userId on the returned orders
        assertEquals(order1.getUserId(), result.get(0).getUserId());
        assertEquals(order2.getUserId(), result.get(1).getUserId());

        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void getOrderById_shouldReturnOrder() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order1));

        Order result = orderService.getOrderById(1L);

        assertNotNull(result);
        assertEquals(order1.getOrderId(), result.getOrderId());
        assertEquals(order1.getUserId(), result.getUserId());
    }

    @Test
    void getOrderById_whenNotFound_shouldThrowException() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.getOrderById(1L));
    }

    @Test
    void deleteOrder_shouldCallDeleteById() {
        when(orderRepository.existsById(1L)).thenReturn(true);

        orderService.deleteOrder(1L);

        verify(orderRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteOrder_whenNotFound_shouldThrowException() {
        when(orderRepository.existsById(1L)).thenReturn(false);

        assertThrows(OrderNotFoundException.class, () -> orderService.deleteOrder(1L));
    }

    @Test
    void updateOrderStatus_shouldUpdateAndReturnUpdatedOrder() {
        Order updatedOrder = new Order();
        updatedOrder.setOrderId(1L);
        updatedOrder.setUserId(order1.getUserId()); // keep same userId
        updatedOrder.setStatus("ready");

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order1));
        when(orderRepository.save(any(Order.class))).thenReturn(updatedOrder);

        Order result = orderService.updateOrderStatus(1L, "ready");

        assertEquals("ready", result.getStatus());
        assertEquals(order1.getUserId(), result.getUserId()); // verify userId unchanged
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void updateOrderStatus_whenOrderNotFound_shouldThrowException() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.updateOrderStatus(1L, "ready"));
    }
}
