package org.example.strongjun.DTOs.req;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderRequest {

    @Email(message = "Invalid email format")
    @NotBlank(message = "Customer email is required")
    private String customerEmail;

    @NotEmpty(message = "Order items cannot be empty")
    private List<OrderItemRequest> orderItems;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OrderItemRequest {
        @NotNull(message = "Product ID is required")
        private Long productId;

        @NotNull(message = "Quantity is required")
        @Min(value = 1, message = "Quantity must be at least 1")
        private Integer quantity;
    }
}
