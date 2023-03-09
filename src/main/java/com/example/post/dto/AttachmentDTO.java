package com.example.post.dto;

import org.springframework.web.multipart.MultipartFile;

public class AttachmentDTO{
    public static class CreationRequest {
        private final MultipartFile files;


        public CreationRequest(MultipartFile file) {
            this.files = file;
        }

        public MultipartFile getFiles() {
            return files;
        }

    }
}
