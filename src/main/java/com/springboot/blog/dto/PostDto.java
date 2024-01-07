package com.springboot.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
@Schema(
        description = "PostDto Model Descritpion"
)
public class PostDto {
    private Long id;

    //////////// SWAGGER ////////////////
    @Schema(
            description = "Blog Post title"
    )
    //////////// SWAGGER ////////////////
    @NotEmpty(message = "Title should not be null or empty")
    @Size(min = 2, message = "Post title should have at least 2 characters")
    private String title;

    //////////// SWAGGER ////////////////
    @Schema(
            description = "Blog Post description"
    )
    //////////// SWAGGER ////////////////
    @NotEmpty(message = "Description should not be null or empty")
    @Size(min = 10, message = "Post description should have at least 10 characters")
    private String description;

    //////////// SWAGGER ////////////////
    @Schema(
            description = "Blog Post content"
    )
    //////////// SWAGGER ////////////////
    @NotEmpty(message = "Content should not be null or empty")
    private String content;

    //////////// SWAGGER ////////////////
    @Schema(
            description = "Blog Post comments associated to post"
    )
    //////////// SWAGGER ////////////////
    private Set<CommentDto> comments;

    //////////// SWAGGER ////////////////
    @Schema(
            description = "Blog Post Category Id"
    )
    //////////// SWAGGER ////////////////
    private Long categoryId;
}
