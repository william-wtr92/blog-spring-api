package com.springboot.blog.service;

import com.springboot.blog.dto.PostDto;

import java.util.List;
import java.util.Optional;

public interface PostService {
    PostDto createPost(PostDto postDto);
    List<PostDto> getAllPosts(int pageNo, int pageSize);
    PostDto getPostById(Long id);
    PostDto updatePost(PostDto postDto, Long id);
    void deletePost(Long id);
}
