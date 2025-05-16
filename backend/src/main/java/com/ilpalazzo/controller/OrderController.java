package com.ilpalazzo.controller;

import com.ilpalazzo.model.entity.Order;
import com.ilpalazzo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ilpalazzo.rabbit.RabbitMQSender;
import com.ilpalazzo.repository.OrderRepository;
import java.util.Map;


import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping
    public ResponseEntity<String> sendMessage(@RequestBody Map<String, Object> body) {
        rabbitMQSender.send(body); // can be any serializable object
        return ResponseEntity.ok("Message sent to RabbitMQ");
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
