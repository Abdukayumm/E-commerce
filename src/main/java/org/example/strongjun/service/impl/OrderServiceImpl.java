package org.example.strongjun.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.strongjun.DTOs.req.CreateOrderRequest;
import org.example.strongjun.DTOs.res.OrderItemResponse;
import org.example.strongjun.DTOs.res.OrderResponse;
import org.example.strongjun.entity.Order;
import org.example.strongjun.entity.OrderItem;
import org.example.strongjun.entity.Product;
import org.example.strongjun.entity.User;
import org.example.strongjun.entity.enums.OrderStatus;
import org.example.strongjun.exception.InsufficientStockException;
import org.example.strongjun.exception.InvalidOrderStatusException;
import org.example.strongjun.exception.OrderNotFoundException;
import org.example.strongjun.exception.ProductNotFoundException;
import org.example.strongjun.repo.OrderRepository;
import org.example.strongjun.repo.ProductRepository;
import org.example.strongjun.repo.UserRepository;
import org.example.strongjun.service.interfaces.OrderService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
        return mapToResponse(order);
    }

    @Override
    public OrderResponse createOrder(CreateOrderRequest request) {

        User user = userRepository.findByEmail(request.getCustomerEmail())
                .orElseThrow(() -> new RuntimeException("User not found with email: " + request.getCustomerEmail()));

        Set<Long> productIds = new HashSet<>();
        for (CreateOrderRequest.OrderItemRequest item : request.getOrderItems()) {
            if (!productIds.add(item.getProductId())) {
                throw new IllegalArgumentException("Duplicate product in order: " + item.getProductId());
            }
        }

        List<Product> products = productRepository.findAllById(productIds);
        Map<Long, Product> productMap = products.stream().collect(Collectors.toMap(Product::getId, p -> p));

        for (CreateOrderRequest.OrderItemRequest item : request.getOrderItems()) {
            Product product = productMap.get(item.getProductId());
            if (product == null || !product.getIsActive()) {
                throw new ProductNotFoundException("Product not found or inactive: " + item.getProductId());
            }
            if (product.getStock() < item.getQuantity()) {
                throw new InsufficientStockException("Insufficient stock for product: " + product.getName());
            }
        }

        Order order = Order.builder()
                .user(user)
                .status(OrderStatus.PENDING)
                .orderItems(new ArrayList<>())
                .build();

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (CreateOrderRequest.OrderItemRequest itemReq : request.getOrderItems()) {
            Product product = productMap.get(itemReq.getProductId());
            BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(itemReq.getQuantity()));

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(itemReq.getQuantity())
                    .unitPrice(product.getPrice())
                    .totalPrice(itemTotal)
                    .build();

            order.getOrderItems().add(orderItem);
            totalAmount = totalAmount.add(itemTotal);

            product.setStock(product.getStock() - itemReq.getQuantity());
            productRepository.save(product);
        }

        order.setTotalAmount(totalAmount);
        Order savedOrder = orderRepository.save(order);
        return mapToResponse(savedOrder);
    }


    @Override
    public OrderResponse updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new InvalidOrderStatusException("Cannot change status of cancelled order");
        }

        order.setStatus(status);
        return mapToResponse(order);
    }

    @Override
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));

        if (order.getStatus() == OrderStatus.CANCELLED) return;

        order.setStatus(OrderStatus.CANCELLED);
        for (OrderItem item : order.getOrderItems()) {
            Product product = item.getProduct();
            product.setStock(product.getStock() + item.getQuantity());
            productRepository.save(product);
        }
    }

    @Override
    public List<OrderResponse> getOrdersByCustomerEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        return orderRepository.findAll().stream()
                .filter(o -> o.getUser().getId().equals(user.getId()))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }


    private OrderResponse mapToResponse(Order order) {
        List<OrderItemResponse> items = order.getOrderItems().stream()
                .map(i -> OrderItemResponse.builder()
                        .id(i.getId())
                        .productId(i.getProduct().getId())
                        .productName(i.getProduct().getName())
                        .quantity(i.getQuantity())
                        .unitPrice(i.getUnitPrice())
                        .totalPrice(i.getTotalPrice())
                        .build())
                .collect(Collectors.toList());

        return OrderResponse.builder()
                .id(order.getId())
                .orderDate(order.getOrderDate())
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .orderItems(items)
                .build();
    }
}
