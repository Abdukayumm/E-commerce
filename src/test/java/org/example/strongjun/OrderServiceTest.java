package org.example.strongjun;

import org.example.strongjun.DTOs.req.CreateOrderRequest;
import org.example.strongjun.entity.Order;
import org.example.strongjun.entity.Product;
import org.example.strongjun.exception.InsufficientStockException;
import org.example.strongjun.repo.OrderItemRepository;
import org.example.strongjun.repo.OrderRepository;
import org.example.strongjun.repo.ProductRepository;
import org.example.strongjun.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        product = Product.builder()
                .id(1L)
                .name("T-Shirt")
                .price(BigDecimal.valueOf(50000))
                .stock(10)
                .isActive(true)
                .build();
    }

    @Test
    void testCreateOrder_InsufficientStock() {
        when(productRepository.findAllById(List.of(1L))).thenReturn(List.of(product));

        CreateOrderRequest request = CreateOrderRequest.builder()
                .customerName("Alice")
                .customerEmail("alice@example.com")
                .orderItems(List.of(
                        CreateOrderRequest.OrderItemRequest.builder()
                                .productId(1L)
                                .quantity(20)
                                .build()
                ))
                .build();

        assertThrows(InsufficientStockException.class, () -> orderService.createOrder(request));
    }

    @Test
    void testCreateOrder_Success() {
        when(productRepository.findAllById(List.of(1L))).thenReturn(List.of(product));
        when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArguments()[0]);

        CreateOrderRequest request = CreateOrderRequest.builder()
                .customerName("Alice")
                .customerEmail("alice@example.com")
                .orderItems(List.of(
                        CreateOrderRequest.OrderItemRequest.builder()
                                .productId(1L)
                                .quantity(2)
                                .build()
                ))
                .build();

        var response = orderService.createOrder(request);
        assertEquals("Alice", response.getCustomerName());
        assertEquals(100000, response.getTotalAmount().intValue());
    }
}
