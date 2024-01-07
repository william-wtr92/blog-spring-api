package com.springboot.blog.service;

import com.springboot.blog.dto.ProductDto;
import com.springboot.blog.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse searchProducts(String query, int pageNo, int pageSize, String sortOrder, String sortBy);
    ProductDto createProduct(ProductDto productDto);
}
