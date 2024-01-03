package com.springboot.blog.service.impl;

import com.springboot.blog.dto.CommentDto;
import com.springboot.blog.dto.CommentResponse;
import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogApiException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {
        // Parse the received data into Java Object
        Comment comment = mapToEntity(commentDto);

        // Checking if postId provided is existing
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        // Set the founded Post with id below into comment
        comment.setPost(post);

        // Call the method into the repository to save the comment
        Comment newComment = commentRepository.save(comment);

        return mapToDto(newComment);
    }

    @Override
    public CommentResponse getCommentsByPostId(Long postId, int pageNo, int pageSize, String sortOrder, String sortBy) {
        // Checking if postId provided is existing
        postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        // Ternary to tell if the order of data is ascending or descending
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // Creation of Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        // Call the custom method in the repository
        Page<Comment> comments = commentRepository.findByPostId(postId, pageable);

        // Parse the data into a List
        List<Comment> listOfComments = comments.getContent();

        // Convert each element of the list into CommentDto
        List<CommentDto> content = listOfComments.stream().map(this::mapToDto).collect(Collectors.toList());

        // Parse the response into CommentResponse to get some information on number of pages, page sizing, last element of list, etc
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setContent(content);
        commentResponse.setPageNo(comments.getNumber());
        commentResponse.setPageSize(comments.getSize());
        commentResponse.setTotalElements(comments.getTotalElements());
        commentResponse.setTotalPages(comments.getTotalPages());
        commentResponse.setLast(comments.isLast());

        return commentResponse;
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        Comment comment = commentBelongPost(postId, commentId);

        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(CommentDto commentDto, Long postId, Long commentId) {
        Comment comment = commentBelongPost(postId, commentId);

        // Bind the new value to the comment
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        // Save the update comment
        Comment newComment = commentRepository.save(comment);

        return mapToDto(newComment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        Comment comment = commentBelongPost(postId, commentId);

        // delete the comment by id
        commentRepository.delete(comment);
    }

    // Convert Entity into DTO
    private CommentDto mapToDto(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());

        return commentDto;
    }

    // Convert DTO into Entitu
    private Comment mapToEntity(CommentDto commentDto){
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        return comment;
    }

    // Usefull method to optimize checks into method below
    private Comment commentBelongPost(Long postId, Long commentId){
        // Checking if postId provided is exsiting
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        // Checking if commentId provided is existing
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        // Throw error if the post_id of comment is different of the post id return by the variable below
        if(!comment.getPost().getId().equals(post.getId())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        return comment;
    }
}
