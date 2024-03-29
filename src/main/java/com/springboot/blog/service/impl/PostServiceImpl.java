package com.springboot.blog.service.impl;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.dto.PostResponse;
import com.springboot.blog.entity.Category;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;

    public PostServiceImpl(PostRepository postRepository, CategoryRepository categoryRepository, ModelMapper mapper) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        // Get category from category id
        Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(() ->
                new ResourceNotFoundException("Category", "id", postDto.getCategoryId())
        );

        // convert DTO to entity from client
        Post post = mapToEntity(postDto);
        // Add the category to post
        post.setCategory(category);

        // save into DataBase the new Post with repository called behind
        Post newPost = postRepository.save(post);

        // convert entity into DTO for client
        PostDto response = mapToDto(newPost);

        return response;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortOrder, String sortBy) {
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                  : Sort.by(sortBy).descending();

        // Creation of Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        // Utilisation of pageable into findAll method
        Page<Post> posts = postRepository.findAll(pageable);

        // Convert Page of Post into List of Post
        List<Post> listOfPosts =  posts.getContent();

        List<PostDto> content =  listOfPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(Long id) {
        Post request = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        return mapToDto(request);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(() ->
                    new ResourceNotFoundException("Category", "id", postDto.getCategoryId())
                );

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        post.setCategory(category);

        Post updatedPost = postRepository.save(post);

        return mapToDto(updatedPost);
    }

    @Override
    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        postRepository.delete(post);
    }

    @Override
    public List<PostDto> getPostsByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", "id", categoryId)
        );

        List<Post> postList = postRepository.findByCategoryId(categoryId);

        List<PostDto> response = postList.stream().map(this::mapToDto).collect(Collectors.toList());

        return response;
    }

    // Convert Entity into DTO
    private PostDto mapToDto(Post post){
        // With model mapper
        PostDto postDto = mapper.map(post, PostDto.class);

        // Without Model Mapper
        /*PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());*/

        return postDto;
    }

    // Convert DTO into Entitu
    private Post mapToEntity(PostDto postDto){
        // With model mapper
        Post post = mapper.map(postDto, Post.class);

        // Without Model Mapper
        /*Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());*/

        return post;
    }
}
