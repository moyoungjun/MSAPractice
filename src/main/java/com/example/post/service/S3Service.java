package com.example.post.service;

import com.example.post.config.S3Config;
import com.example.post.properties.S3Properties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.utils.IoUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

@Service
public class S3Service {
    S3Properties s3Properties = new S3Properties();
    private final S3Config s3config;

    public S3Service(S3Config s3config) {
        this.s3config = s3config;
    }

    public CompletableFuture<PutObjectResponse> uploadFile(String filename, byte[] bytes, String contentType){
        System.out.println("로그 테스트");
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(s3Properties.getBucketName())
                .key(filename)
                .contentType(contentType)
                .build();
        System.out.println("테스트2");
        return  s3config.s3client().putObject(objectRequest, AsyncRequestBody.fromBytes(bytes));
    }

    public void upload(MultipartFile multipartFile) throws IOException {
        try(InputStream inputStream = multipartFile.getInputStream()) {
            String key = multipartFile.getOriginalFilename();
            byte[] bytes = IoUtils.toByteArray(inputStream);

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            PutObjectRequest putObjectRequest  = PutObjectRequest.builder()
                    .bucket(s3Properties.getBucketName())
                    .key(key)
                    .contentType("txt")
                    .build();
            s3config.s3client().putObject(putObjectRequest, AsyncRequestBody.fromBytes(bytes));
            byteArrayInputStream.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        };
    }


}
