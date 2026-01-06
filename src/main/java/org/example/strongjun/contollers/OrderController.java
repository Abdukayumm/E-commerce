package org.example.strongjun.contollers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.strongjun.DTOs.req.CreateOrderRequest;
import org.example.strongjun.DTOs.res.OrderResponse;
import org.example.strongjun.entity.enums.OrderStatus;
import org.example.strongjun.service.interfaces.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public List<OrderResponse> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public OrderResponse getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @PostMapping
    public OrderResponse createOrder(@Valid @RequestBody CreateOrderRequest request) {
        return orderService.createOrder(request);
    }

    @PutMapping("/{id}/status")
    public OrderResponse updateOrderStatus(@PathVariable Long id, @RequestParam OrderStatus status) {
        return orderService.updateOrderStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public void cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
    }

    @GetMapping("/customer/{email}")
    public List<OrderResponse> getOrdersByCustomer(@PathVariable String email) {
        return orderService.getOrdersByCustomerEmail(email);
    }
}
