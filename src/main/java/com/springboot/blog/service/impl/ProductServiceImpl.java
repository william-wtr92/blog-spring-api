package com.springboot.blog.service.impl;

import com.springboot.blog.dto.ProductDto;
import com.springboot.blog.entity.Product;
import com.springboot.blog.repository.ProductRepository;
import com.springboot.blog.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper mapper;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper mapper) {
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    @Override
    public List<ProductDto> searchProducts(String query) {
        List<Product> products = productRepository.searchProducts(query);

        List<ProductDto> productDtoList = products.stream().map(product ->
                        mapper.map(product, ProductDto.class)
                )
                .collect(Collectors.toList());

        return productDtoList;
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setSku(productDto.getSku());
        product.setImageUrl(productDto.getImageUrl());
        product.setActive(productDto.isActive());

        Product newProduct = productRepository.save(product);

        return mapper.map(newProduct, ProductDto.class);
    }
}
