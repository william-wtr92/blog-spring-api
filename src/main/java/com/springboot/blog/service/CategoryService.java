package com.springboot.blog.service;

import com.springboot.blog.dto.CategoryDto;
import com.springboot.blog.dto.CategoryResponse;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto getCategory(Long categoryId);
    CategoryResponse getAllCategories(int pageNo, int pageSize, String sortBy, String sortOrder);
    CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto);
}
