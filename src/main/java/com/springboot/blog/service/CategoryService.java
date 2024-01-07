package com.springboot.blog.service;

import com.springboot.blog.dto.CategoryDto;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto getCategory(Long categoryId);
}
