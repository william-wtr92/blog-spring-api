package com.springboot.blog.service.impl;

import com.springboot.blog.dto.CategoryDto;
import com.springboot.blog.entity.Category;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        // Map the DTO in JAVA object
        Category category = mapToEntity(categoryDto);

        // Save the new object into database
        Category newCategory = categoryRepository.save(category);

        // Return into DTO object the response to the client
        return mapToDto(newCategory);
    }

    // Map Category Java Object to DTO
    private CategoryDto mapToDto(Category category){
        CategoryDto categoryDto = mapper.map(category, CategoryDto.class);

        return categoryDto;
    }

    // Map Category DTO to Java Object
    private Category mapToEntity(CategoryDto categoryDto){
        Category category = mapper.map(categoryDto, Category.class);

        return category;
    }
}
