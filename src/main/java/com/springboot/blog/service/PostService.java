package com.springboot.blog.service;

import com.springboot.blog.dto.PostDto;

public interface PostService {
    PostDto createPost(PostDto postDto);
}
