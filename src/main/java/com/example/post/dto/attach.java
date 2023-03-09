package com.example.post.dto;

import org.springframework.web.multipart.MultipartFile;

public record attach(MultipartFile files) {
}
