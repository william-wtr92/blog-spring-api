package com.springboot.blog.service;

import com.springboot.blog.dto.CommentDto;
import com.springboot.blog.dto.CommentResponse;

public interface CommentService {
    CommentDto createComment(Long postId, CommentDto commentDto);
    CommentResponse getCommentsByPostId(Long postId, int pageNo, int pageSize, String sortOrder, String sortBy);
    CommentDto getCommentById(Long postId, Long commentId);
    CommentDto updateComment(CommentDto commentDto,Long postId, Long commentId);
}
