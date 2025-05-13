package com.ilpalazzo.service.impl;
import com.ilpalazzo.errors.OrderNotFoundException;
import com.ilpalazzo.model.entity.Order;
import com.ilpalazzo.repository.OrderRepository;
import com.ilpalazzo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

<<<<<<< HEAD
=======
    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;

>>>>>>> 5fe42afdefeac7a96016c14c99c3c15ff5d1a822
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order placeOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
<<<<<<< HEAD
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
=======
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
>>>>>>> 5fe42afdefeac7a96016c14c99c3c15ff5d1a822
    }

    @Override
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new OrderNotFoundException(id);
        }
        orderRepository.deleteById(id);
    }
}
