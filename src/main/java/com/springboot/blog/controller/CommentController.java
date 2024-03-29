package com.springboot.blog.controller;

import com.springboot.blog.dto.CommentDto;
import com.springboot.blog.dto.CommentResponse;
import com.springboot.blog.service.CommentService;
import com.springboot.blog.utils.AppConstants;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/")
@Tag(
        name = "CRUD Rest API for Comment Resource"
)
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("posts/{id}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable("id") Long postId, @Valid @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }

    @GetMapping("posts/{id}/comments")
    public CommentResponse getCommentsByPostId(
            @PathVariable("id") Long postId,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = AppConstants.DEFAULT_SORT_ORDER, required = false) String sortOrder
    ) {
        return commentService.getCommentsByPostId(postId, pageNo, pageSize, sortOrder, sortBy);
    }

    @GetMapping("posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(
            @PathVariable("postId") Long postId,
            @PathVariable("commentId") Long commentId
    ){
        return ResponseEntity.ok(commentService.getCommentById(postId, commentId));
    }

    @PutMapping("posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(
            @Valid @RequestBody CommentDto commentDto,
            @PathVariable("postId") Long postId,
            @PathVariable("commentId") Long commentId)
    {
        return new ResponseEntity<>(commentService.updateComment(commentDto, postId, commentId), HttpStatus.OK);
    }

    @DeleteMapping("posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(
            @PathVariable("postId") Long postId,
            @PathVariable("commentId") Long commentId
    ){
        commentService.deleteComment(postId, commentId);

        return ResponseEntity.ok("Comment with id: " + commentId + " have been deleted !");
    }
}
