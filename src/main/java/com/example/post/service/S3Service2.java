package com.example.post.service;


import com.example.post.properties.S3Properties;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.core.async.AsyncResponseTransformer;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class S3Service2 {
    S3AsyncClient s3AsyncClient;
    private S3Properties s3Properties;

    public S3Service2(S3Properties s3Properties) {
        this.s3Properties = s3Properties;
    }

    private S3AsyncClient getS3AsyncClient() {
        Region region = Region.AP_NORTHEAST_2;
        AwsCredentials credentials = AwsBasicCredentials.create(s3Properties.getAccessKey(), s3Properties.getSecretKey());
        return S3AsyncClient.builder()
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .region(region)
                .build();
    }

    public byte[] getObjectBytes(String BucketName, String KeyName) {
        s3AsyncClient = getS3AsyncClient();
        final AtomicReference<byte[]> reference = new AtomicReference<>();
        try {
            GetObjectRequest objectRequest = GetObjectRequest.builder()
                    .bucket(BucketName)
                    .key(KeyName)
                    .build();
            final CompletableFuture<ResponseBytes<GetObjectResponse>>[] futureGet = new CompletableFuture[]{s3AsyncClient.getObject(objectRequest,
                    AsyncResponseTransformer.toBytes())};

            futureGet[0].whenComplete((resp, err) -> {
                try {
                    if (resp != null) {
                        reference.set(resp.asByteArray());
                    } else {
                        err.printStackTrace();
                    }
                } finally {
                    getS3AsyncClient().close();
                }
            });
            futureGet[0].join();

            return reference.get();
        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
        return null;
    }

    public String putObject(byte[] data, String filename, String contentType) {
        s3AsyncClient = getS3AsyncClient();

        try {
            PutObjectRequest objectRequest = PutObjectRequest.builder()
                    .bucket(s3Properties.getBucketName())
                    .key(filename)
                    .contentType(contentType)
                    .build();
            CompletableFuture<PutObjectResponse> future = s3AsyncClient.putObject(objectRequest,
                    AsyncRequestBody.fromBytes(data));

            future.whenComplete((resp, err) -> {
                try {
                    if (resp != null) {
                        System.out.println("Object up" + resp);
                    } else {
                        err.printStackTrace();
                    }
                } finally {
                    s3AsyncClient.close();
                }
            });
            future.join();
            return filename + "good success";
        } catch (S3Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return "";
    }
}
