package com.example.post.controller;


import com.example.post.dao.PostRepository;
import com.example.post.dto.PostDTO;
import com.example.post.dto.attach;
import com.example.post.entity.Post;
import com.example.post.service.PostService;
import com.example.post.service.S3Service;
import com.example.post.service.S3Service2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;


@RequestMapping("/main")
@RestController
public class test {
    private final Logger log = LoggerFactory.getLogger("g");
    private final PostRepository postRepository;
    private final PostService postService;
    private final S3Service s3Service;
    S3Service2 s3Service2;

    public test(PostRepository test, PostService postService, S3Service s3Service, S3Service2 s3Service2) {
        this.postRepository = test;
        this.postService = postService;
        this.s3Service = s3Service;
        this.s3Service2 = s3Service2;
    }

    @GetMapping
    public Flux<Post> gets(){
        Flux<Post> test = postRepository.findAll();
        return test;
    }
    @PostMapping
    public Mono<PostDTO.Response> posts(@RequestBody PostDTO.CreationRequest creationRequest) {
        return postService.posts(creationRequest);
    }

    @PostMapping("/hi")
    public Mono<attach> test(@RequestPart("file") MultipartFile attachment) {
        try{
        log.info("aa");
        UUID A = UUID.randomUUID();
        String filename = attachment.getOriginalFilename();
        byte[] bytes = attachment.getBytes();
        String contentType = attachment.getContentType();
        log.info(attachment.getContentType());

        CompletableFuture<PutObjectResponse> a = s3Service.uploadFile(filename,bytes,contentType);

        return (Mono<attach>) attachment;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        //s3Service.upload(attachment.getFile());
        return null;
    }
    @PostMapping("/test")
    public String upload(@ModelAttribute("file") FilePart file, ServerWebExchange exchange) {
        byte[] bytes  = file.content().toIterable().toString().getBytes();
        String filename = file.filename();
        String contentType = file.headers().getContentType().toString();

        //s3Service.uploadFile(filename,bytes,contentType);
        return s3Service2.putObject(bytes, filename,contentType);
    }

    @PostMapping("/asynctest")
    public void singfile(@ModelAttribute("file") MultipartFile file) throws IOException {
        System.out.println("asyntest");
        byte[] by = file.getBytes();
        String na = file.getOriginalFilename();
        s3Service2.putObject(by,"goominzomin-post",na);
    }
}

