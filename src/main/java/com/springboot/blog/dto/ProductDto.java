package com.springboot.blog.dto;


import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductDto {
    private Long id;

    @NotEmpty(message = "Sku should not be null or empty")
    private String sku;

    @NotEmpty(message = "Name should not be null or empty")
    private String name;

    @NotEmpty(message = "Description should not be null or empty")
    private String description;

    private boolean active;

    @NotEmpty(message = "ImageUrl should not be null or empty")
    private String imageUrl;

    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
}
