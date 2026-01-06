package org.example.strongjun.service.interfaces;


import org.example.strongjun.DTOs.res.ProductResponse;
import org.example.strongjun.entity.Product;

import java.util.List;

public interface ProductService {

    List<ProductResponse> getAllProducts(int page, int size);

    ProductResponse getProductById(Long id);

    ProductResponse createProduct(Product product);

    ProductResponse updateProduct(Long id, Product product);

    void deleteProduct(Long id);

    List<ProductResponse> searchProducts(String name, String category);

}
