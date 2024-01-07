package com.springboot.blog.controller;

import com.springboot.blog.dto.CategoryDto;
import com.springboot.blog.dto.CategoryResponse;
import com.springboot.blog.service.CategoryService;
import com.springboot.blog.utils.AppConstants;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/categories")
@Tag(
        name = "CRUD Rest API for Category Resource"
)
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Create a new category, secure with ADMIN Role Authorization
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto response = categoryService.createCategory(categoryDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Get Category by id
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable("id") Long categoryId){
        CategoryDto response = categoryService.getCategory(categoryId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get all categories with pagination
    @GetMapping
    public ResponseEntity<CategoryResponse> getAllCategories(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = AppConstants.DEFAULT_SORT_ORDER, required = false) String sortOrder
    ){
        CategoryResponse response = categoryService.getAllCategories(pageNo, pageSize, sortBy, sortOrder);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Update Category by id
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable("id") Long categoryId,
                                                      @Valid @RequestBody CategoryDto categoryDto){
        CategoryDto response = categoryService.updateCategory(categoryId, categoryDto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Delete Category by id
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Long categoryId){
        categoryService.deleteCategory(categoryId);

        return new ResponseEntity<>("Category with id: " + categoryId + " has been deleted !", HttpStatus.OK);
    }
}
