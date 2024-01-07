package com.springboot.blog.service.impl;

import com.springboot.blog.dto.ProductDto;
import com.springboot.blog.dto.ProductResponse;
import com.springboot.blog.entity.Product;
import com.springboot.blog.repository.ProductRepository;
import com.springboot.blog.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public ProductResponse searchProducts(String query, int pageNo, int pageSize, String sortOrder, String sortBy) {
        // Ternary to tell if the order of data is ascending or descending
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // Creation of Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        // Call the custom method in the repository
        Page<Product> products = productRepository.searchProducts(query, pageable);

        // Parse the date into a List
        List<Product> productList = products.getContent();

        // Parse each element of productList into ProductDto
        List<ProductDto> productDtoList = productList.stream().map(product -> mapper.map(product, ProductDto.class)).collect(Collectors.toList());

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDtoList);
        productResponse.setPageNo(products.getNumber());
        productResponse.setPageSize(products.getSize());
        productResponse.setTotalElements(products.getTotalElements());
        productResponse.setTotalPages(products.getTotalPages());
        productResponse.setLast(products.isLast());

        return productResponse;
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
