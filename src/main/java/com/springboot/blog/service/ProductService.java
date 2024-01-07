package com.springboot.blog.service;

import com.springboot.blog.dto.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> searchProducts(String query);
    ProductDto createProduct(ProductDto productDto);
}
