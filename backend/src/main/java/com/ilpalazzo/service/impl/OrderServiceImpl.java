package com.ilpalazzo.service.impl;

import com.ilpalazzo.errors.OrderNotFoundException;
import com.ilpalazzo.mapper.OrderMapper;
import com.ilpalazzo.model.dto.OrderRequestDto;
import com.ilpalazzo.model.dto.OrderResponseDto;
import com.ilpalazzo.model.entity.Order;
import com.ilpalazzo.model.entity.OrderItem;
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
        Order order = OrderMapper.toEntity(dto);  // Map DTO to Entity

        // Handling the persistence of Order and its OrderItems
        Order savedOrder = orderRepository.save(order);

        return OrderMapper.toResponse(savedOrder);  // Map saved entity back to DTO
    }

    @Override
    public List<OrderResponseDto> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(OrderMapper::toResponse)  // Mapping each order entity to its DTO
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponseDto getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));  // Custom exception

        return OrderMapper.toResponse(order);  // Convert to DTO for response
    }

    @Override
    public OrderResponseDto updateOrder(Long id, OrderRequestDto dto) {
        log.info("Action: in OrderServiceImpl.updateOrder looking for order with id {} and update object is {}", id, dto);
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));  // Handle not found error

        log.info("Action: in OrderServiceImpl.updateOrder order found: {}", order);

        order.setTableId(dto.getTableId());
        order.setTotalAmount(dto.getTotalAmount());
        // Assuming status and items might also be updated, you can handle this here if needed.

        log.info("Action: in OrderServiceImpl.updateOrder order updated: {}", order);

        Order updatedOrder = orderRepository.save(order);

        log.info("Action: in OrderServiceImpl.updateOrder order saved: {}", updatedOrder);

        return OrderMapper.toResponse(updatedOrder);  // Return the updated order as DTO
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
