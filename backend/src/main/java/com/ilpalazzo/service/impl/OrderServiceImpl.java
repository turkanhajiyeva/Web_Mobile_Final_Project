package com.ilpalazzo.service.impl;

import com.ilpalazzo.errors.OrderNotFoundException;
import com.ilpalazzo.mapper.OrderMapper;
import com.ilpalazzo.model.dto.OrderRequestDto;
import com.ilpalazzo.model.dto.OrderResponseDto;
import com.ilpalazzo.model.entity.Order;
import com.ilpalazzo.repository.OrderRepository;
import com.ilpalazzo.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderResponseDto createOrder(OrderRequestDto dto) {
        Order order = OrderMapper.toEntity(dto);
        Order savedOrder = orderRepository.save(order);
        return OrderMapper.toResponse(savedOrder);
    }

    @Override
    public List<OrderResponseDto> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(OrderMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponseDto getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        return OrderMapper.toResponse(order);
    }

    @Override
    public OrderResponseDto updateOrder(Long id, OrderRequestDto dto) {
        log.info("Updating order with ID {} using data: {}", id, dto);

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        order.setTableId(dto.getTableId());
        order.setTotalAmount(dto.getTotalAmount());
        order.setItems(OrderMapper.mapOrderItems(dto.getItems(), order)); // re-map items

        Order updatedOrder = orderRepository.save(order);

        log.info("Order updated and saved: {}", updatedOrder);

        return OrderMapper.toResponse(updatedOrder);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
