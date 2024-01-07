package com.springboot.blog.service.impl;

import com.springboot.blog.dto.CategoryDto;
import com.springboot.blog.dto.CategoryResponse;
import com.springboot.blog.entity.Category;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public CategoryDto getCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", "id", categoryId)
        );

        return mapToDto(category);
    }

    @Override
    public CategoryResponse getAllCategories(int pageNo, int pageSize, String sortBy, String sortOrder) {
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // Creation of Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        // Find all categories with pagination
        Page<Category> categories = categoryRepository.findAll(pageable);

        // Convert them into List of Category
        List<Category> categoryList = categories.getContent();

        // Convert categoryList into List of CategoryDto
        List<CategoryDto> categoryDtoList = categoryList.stream().map(this::mapToDto).collect(Collectors.toList());

        // Create new Category Object to append to them the data
        CategoryResponse response = new CategoryResponse();
        response.setContent(categoryDtoList);
        response.setPageNo(categories.getNumber());
        response.setPageSize(categories.getSize());
        response.setTotalElements(categories.getTotalElements());
        response.setTotalPages(categories.getTotalPages());
        response.setLast(categories.isLast());

        return response;
    }

    @Override
    public CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto) {
        // Check if CategoryId exist in database
        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", "id", categoryId)
        );

        // Add new value to update the category found below
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        // Save the updated category into database
        Category updatedCategory = categoryRepository.save(category);

        return mapToDto(updatedCategory);
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
