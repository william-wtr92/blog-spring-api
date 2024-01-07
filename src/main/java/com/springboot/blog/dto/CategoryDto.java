package com.springboot.blog.dto;

import com.springboot.blog.entity.Post;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CategoryDto {
    private Long id;

    @NotEmpty(message = "Category name must be not empty or null")
    @Size(min = 3, message = "Category name must have 3 characters minimum")
    private String name;

    @NotEmpty(message = "Category name must be not empty or null")
    @Size(min = 10, message = "Category description must have 10 characters minimum")
    private String description;

    private List<PostDto> posts;
}
