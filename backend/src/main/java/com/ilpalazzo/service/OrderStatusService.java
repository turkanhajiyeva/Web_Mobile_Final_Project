package com.ilpalazzo.service;

import com.ilpalazzo.model.entity.Order;
import com.ilpalazzo.config.*;
import com.ilpalazzo.security.*;
import com.ilpalazzo.repository.OrderRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
public class OrderStatusService {

    private static final String ORDER_STATUS_PREFIX = "order_status:";

    private final RedisTemplate<String, Object> redisTemplate;
    private final OrderRepository orderRepository;

    public OrderStatusService(RedisTemplate<String, Object> redisTemplate,
                              OrderRepository orderRepository) {
        this.redisTemplate = redisTemplate;
        this.orderRepository = orderRepository;
    }

    public String getOrderStatus(Long orderId) {
        String key = ORDER_STATUS_PREFIX + orderId;
        Object cachedStatus = redisTemplate.opsForValue().get(key);
        if (cachedStatus != null) {
            return (String) cachedStatus;
        }
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            String status = orderOpt.get().getStatus();
            redisTemplate.opsForValue().set(key, status, Duration.ofMinutes(30));
            return status;
        }
        return null;
    }

    public void updateOrderStatus(Long orderId, String newStatus) {
        orderRepository.findById(orderId).ifPresent(order -> {
            order.setStatus(newStatus);
            orderRepository.save(order);

            String key = ORDER_STATUS_PREFIX + orderId;
            redisTemplate.opsForValue().set(key, newStatus, Duration.ofMinutes(30));
        });
    }
}
