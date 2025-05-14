package com.ilpalazzo.controller;

import com.ilpalazzo.model.entity.Order;
import com.ilpalazzo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ilpalazzo.rabbit.OrderMessageProducer;
import com.ilpalazzo.repository.OrderRepository;
import java.util.Map;


import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderMessageProducer orderMessageProducer;

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping
    public ResponseEntity<String> placeOrder(@RequestBody Order order) {
        orderMessageProducer.sendOrder(order); // send the order object to the queue
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
