package com.springboot.blog.controller;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.dto.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/posts")
@Tag(
        name = "CRUD Rest API for Post Resource"
)
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //////////// SWAGGER API ////////////////////////
    /* @Operation / @ApiResponse / @SecurityRequirement is used for Swagger Documentation to provide users friendly
     explanation in http://localhost:8080/swagger-ui/index.html/ */
    @Operation(
            summary = "Create Post REST API",
            description = "Create Post REST API is used to save post into database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Statut 201 CREATED"
    )
    //
    /* You need to add @SecurityRequirement after custom ur SecurityConfig with Scheme to provide the possibility to add
       JWT Token in Swagger Web  */
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    //////////// SWAGGER API ////////////////////////
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    //////////// SWAGGER API ////////////////////////
    @Operation(
            summary = "GET All Post REST API",
            description = "Get All Post REST API is used to get all post from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Statut 200 OK"
    )
    //////////// SWAGGER API ////////////////////////
    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = AppConstants.DEFAULT_SORT_ORDER, required = false) String sortOrder
    ){
        return postService.getAllPosts(pageNo, pageSize, sortOrder, sortBy);
    }

    //////////// SWAGGER API ////////////////////////
    @Operation(
            summary = "GET Post by id REST API",
            description = "Get Post by id REST API is used to get post by id from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Statut 200 OK"
    )
    //////////// SWAGGER API ////////////////////////
    @GetMapping("{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") Long postId){
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    //////////// SWAGGER API ////////////////////////
    /* @Operation / @ApiResponse / @SecurityRequirement is used for Swagger Documentation to provide users friendly
     explanation in http://localhost:8080/swagger-ui/index.html/ */
    @Operation(
            summary = "Update Post REST API",
            description = "Update Post REST API is used to update post into database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Statut 200 OK"
    )
    //
    /* You need to add @SecurityRequirement after custom ur SecurityConfig with Scheme to provide the possibility to add
       JWT Token in Swagger Web  */
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    //////////// SWAGGER API ////////////////////////
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable("id") Long postId){
        PostDto postResponse = postService.updatePost(postDto, postId);

        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    //////////// SWAGGER API ////////////////////////
    /* @Operation / @ApiResponse / @SecurityRequirement is used for Swagger Documentation to provide users friendly
     explanation in http://localhost:8080/swagger-ui/index.html/ */
    @Operation(
            summary = "Delete Post REST API",
            description = "Delete Post REST API is used to delete post into database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Statut 200 OK"
    )
    //
    /* You need to add @SecurityRequirement after custom ur SecurityConfig with Scheme to provide the possibility to add
       JWT Token in Swagger Web  */
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    //////////// SWAGGER API ////////////////////////
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") Long postId){
        postService.deletePost(postId);

        return new ResponseEntity<>("The ressource "+ postId + " has been deleted !", HttpStatus.OK);
    }

    //////////// SWAGGER API ////////////////////////
    @Operation(
            summary = "GET Posts by Category Id REST API",
            description = "Get Posts by Category Id REST API is used to get posts by Category from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Statut 200 OK"
    )
    //////////// SWAGGER API ////////////////////////
    @GetMapping("/category/{id}")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable("id") Long categoryId){
        List<PostDto> response = postService.getPostsByCategory(categoryId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
