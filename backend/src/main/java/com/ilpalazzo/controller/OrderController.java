package com.ilpalazzo.controller;

import com.ilpalazzo.model.entity.Order;
import com.ilpalazzo.service.OrderService;
import com.ilpalazzo.service.OrderStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.ilpalazzo.rabbit.RabbitMQSender;
import com.ilpalazzo.repository.OrderRepository;
import java.util.Map;
import com.ilpalazzo.model.dto.OrderRequestDto;
import com.ilpalazzo.model.dto.OrderNotificationDto;
import com.ilpalazzo.mapper.*;
import com.ilpalazzo.model.dto.OrderResponseDto;
import com.ilpalazzo.service.MenuItemService;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderStatusService orderStatusService;

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MenuItemService menuItemService;

    @PostMapping
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequestDto orderRequest) {
        try {
            Order order = OrderMapper.toEntity(orderRequest);
            Order savedOrder = orderService.placeOrder(order);

            OrderNotificationDto notification = new OrderNotificationDto();
            notification.setOrderId(savedOrder.getOrderId());
            notification.setUserId(savedOrder.getUserId());
            notification.setStatus(savedOrder.getStatus());
            notification.setMessage("Your order is now being prepared!");

            rabbitMQSender.sendNotification(notification);

            OrderResponseDto responseDto = OrderMapper.toResponse(savedOrder, menuItemService);

            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
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
        orderStatusService.updateOrderStatus(id, newStatus);

        Order updatedOrder = orderService.getOrderById(id);
        return ResponseEntity.ok(updatedOrder);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Order>> getOrdersByStatus(@PathVariable String status) {
        List<Order> orders = orderRepository.findByStatus(status);
        return ResponseEntity.ok(orders);
    }

    // New endpoint for cached status only (optional)
    @GetMapping("/{id}/cached-status")
    public ResponseEntity<String> getCachedOrderStatus(@PathVariable Long id) {
        String status = orderStatusService.getOrderStatus(id);
        if (status == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(status);
    }
}
