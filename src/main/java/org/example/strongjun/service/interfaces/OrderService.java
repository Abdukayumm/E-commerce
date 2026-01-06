package org.example.strongjun.service.interfaces;

import org.example.strongjun.DTOs.req.CreateOrderRequest;
import org.example.strongjun.DTOs.res.OrderResponse;
import org.example.strongjun.entity.enums.OrderStatus;

import java.util.List;

public interface OrderService {

    List<OrderResponse> getAllOrders();

    OrderResponse getOrderById(Long id);

    OrderResponse createOrder(CreateOrderRequest request);

    OrderResponse updateOrderStatus(Long orderId, OrderStatus status);

    void cancelOrder(Long orderId);

    List<OrderResponse> getOrdersByCustomerEmail(String email);
}
