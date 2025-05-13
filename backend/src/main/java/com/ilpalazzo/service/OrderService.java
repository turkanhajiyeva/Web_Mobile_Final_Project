package com.ilpalazzo.service;

<<<<<<< HEAD
import com.ilpalazzo.model.entity.Order;
=======
import com.ilpalazzo.model.dto.OrderRequestDto;
import com.ilpalazzo.model.dto.OrderResponseDto;
>>>>>>> 5fe42afdefeac7a96016c14c99c3c15ff5d1a822

import java.util.List;

public interface OrderService {
<<<<<<< HEAD
    Order placeOrder(Order order);
    List<Order> getAllOrders();
    Order getOrderById(Long id);
=======

    OrderResponseDto createOrder(OrderRequestDto orderRequestDto);

    List<OrderResponseDto> getAllOrders();

    OrderResponseDto getOrderById(Long id);

    OrderResponseDto updateOrder(Long id, OrderRequestDto orderRequestDto);

>>>>>>> 5fe42afdefeac7a96016c14c99c3c15ff5d1a822
    void deleteOrder(Long id);
}
