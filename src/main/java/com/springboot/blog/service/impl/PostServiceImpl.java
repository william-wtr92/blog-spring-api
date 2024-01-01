package com.springboot.blog.service.impl;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.entity.Post;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        // convert DTO to entity from client
        Post post = mapToEntity(postDto);

        // save into DataBase the new Post with repository called behind
        Post newPost = postRepository.save(post);

        // convert entity into DTO for client
        PostDto response = mapToDto(newPost);

        return response;
    }

    @Override
    public List<PostDto> getAllPosts() {
        List<Post> request = postRepository.findAll();

        // Iterate on each element of request to convert them in PostDto and return the result cast in a List
        return request.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
    }

    // Convert Entity into DTO
    private PostDto mapToDto(Post post){
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());

        return postDto;
    }

    // Convert DTO into Entitu
    private Post mapToEntity(PostDto postDto){
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        return post;
    }
}
