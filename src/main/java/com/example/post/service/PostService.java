package com.example.post.service;

import com.example.post.dao.PostRepository;
import com.example.post.dto.PostDTO;
import com.example.post.entity.Post;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Mono<PostDTO.Response> posts(PostDTO.CreationRequest creationRequest) {
        Post post = new Post(creationRequest);
        return postRepository.save(post).map(PostDTO.Response::new);
    }



}
