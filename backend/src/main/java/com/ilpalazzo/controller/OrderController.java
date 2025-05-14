package com.ilpalazzo.controller;

import com.ilpalazzo.model.entity.Order;
import com.ilpalazzo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ilpalazzo.rabbit.OrderMessageProducer;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderMessageProducer orderMessageProducer;

    @PostMapping
    public ResponseEntity<String> placeOrder(@RequestBody Order order) {
        orderMessageProducer.sendOrder(order.toString()); // send to queue
        return ResponseEntity.accepted().body("Order is being processed asynchronously.");
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
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id, @RequestBody String newStatus) {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, newStatus));
    }

}
