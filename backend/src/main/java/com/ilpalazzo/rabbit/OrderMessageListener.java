package com.ilpalazzo.rabbit;

import com.ilpalazzo.model.entity.Order;
import com.ilpalazzo.repository.OrderRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderMessageListener {

    private final OrderRepository orderRepository;

    public OrderMessageListener(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @RabbitListener(queues = "${sample.rabbitmq.queue}")
    public void receiveOrder(Order order) {
        try {
            System.out.println("Received order: " + order);
            orderRepository.save(order);
            System.out.println("Order saved to database.");
        } catch (Exception e) {
            System.err.println("Failed to save order: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
