package com.example.post.dto;

public record UserDTO(
        Long id,
        String username,
        String email,
        String name
) {
}
