package com.ilpalazzo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ilpalazzo.service.OrderService;
import com.ilpalazzo.model.entity.Order;
import com.ilpalazzo.dto.OrderRequest;
import com.ilpalazzo.dto.OrderStatusUpdate;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest orderRequest) {
        return ResponseEntity.ok(orderService.createOrder(orderRequest));
    }

    @GetMapping("/status/{orderId}")
    public ResponseEntity<Order> getOrderStatus(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestBody OrderStatusUpdate statusUpdate) {
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, statusUpdate.getStatus()));
    }
}