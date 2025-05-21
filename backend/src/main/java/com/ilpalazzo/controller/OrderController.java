package com.ilpalazzo.controller;

import com.ilpalazzo.model.entity.Order;
import com.ilpalazzo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ilpalazzo.rabbit.RabbitMQSender;
import com.ilpalazzo.repository.OrderRepository;
import java.util.Map;
import com.ilpalazzo.model.dto.OrderRequestDto;
import com.ilpalazzo.mapper.*;
import com.ilpalazzo.model.dto.OrderResponseDto;
import com.ilpalazzo.service.MenuItemService;

import java.util.List;
import java.util.HashMap;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MenuItemService menuItemService;

    @PostMapping
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequestDto orderRequest) {
        try {
            // Convert DTO to entity
            Order order = OrderMapper.toEntity(orderRequest);
            
            // Save order to database
            Order savedOrder = orderService.placeOrder(order);
            
            // Send to RabbitMQ for async processing
            rabbitMQSender.send(savedOrder);
            
            // Convert to response DTO with menu item details
            OrderResponseDto responseDto = OrderMapper.toResponse(savedOrder, menuItemService);
            
            // Return the saved order with its ID
            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to place order: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(
        @PathVariable Long id,
        @RequestBody Map<String, String> body) {

        String newStatus = body.get("status");
        Order order = orderRepository.findById(id).orElseThrow();
        order.setStatus(newStatus);
        orderRepository.save(order);

        return ResponseEntity.ok(order);
    }

    @GetMapping("/{id}/status/{status}")
    public ResponseEntity<List<Order>> getOrdersByStatus(@PathVariable String status) {
    List<Order> orders = orderRepository.findByStatus(status);
    return ResponseEntity.ok(orders);
    }

}
