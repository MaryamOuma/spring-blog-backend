package com.programinmgtechie.springblog.service;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.programinmgtechie.springblog.service.AuthService;

import exception.PostNotFoundException;

import com.programinmgtechie.springblog.dto.PostDto;
import com.programinmgtechie.springblog.model.Post;

import com.programinmgtechie.springblog.repository.PostRepository;
import static java.util.stream.Collectors.toList;
import jakarta.transaction.Transactional;

@Service
public class PostService {

   
    @Autowired
    private PostRepository postRepository;
    @Autowired
	private AuthService authService;

    @Transactional
    public List<PostDto> showAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(this::mapFromPostToDto).collect(toList());
    }

    @Transactional
    public void createPost(PostDto postDto) {
        Post post = mapFromDtoToPost(postDto);
        postRepository.save(post);
    }

    @Transactional
    public PostDto readSinglePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("For id " + id));
        return mapFromPostToDto(post);
    }

    private PostDto mapFromPostToDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setUsername(post.getUsername());
        return postDto;
    }

    private Post mapFromDtoToPost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());

        // Assuming getCurrentUser() returns an instance of com.programinmgtechie.springblog.model.User
        com.programinmgtechie.springblog.model.User loggedInUser = authService.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        post.setCreatedOn(Instant.now());
        post.setUsername(loggedInUser.getUsername());
        post.setUpdatedOn(Instant.now());
        System.out.println("thepost" + post);
        return post;
    }


}