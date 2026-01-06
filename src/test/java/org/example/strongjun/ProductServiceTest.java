package org.example.strongjun;

import org.example.strongjun.entity.Product;
import org.example.strongjun.exception.ProductNotFoundException;
import org.example.strongjun.repo.ProductRepository;
import org.example.strongjun.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        product = Product.builder()
                .id(1L)
                .name("T-Shirt")
                .price(BigDecimal.valueOf(50000))
                .stock(100)
                .category("Clothes")
                .isActive(true)
                .build();
    }

    @Test
    void testGetProductById_Success() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        var response = productService.getProductById(1L);
        assertEquals("T-Shirt", response.getName());
    }

    @Test
    void testGetProductById_NotFound() {
        when(productRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(2L));
    }

    @Test
    void testCreateProduct() {
        when(productRepository.save(product)).thenReturn(product);
        var response = productService.createProduct(product);
        assertEquals("T-Shirt", response.getName());
    }
}
