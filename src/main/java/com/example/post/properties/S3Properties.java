package com.example.post.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties("aws.s3")
public class S3Properties {
    private String accessKey;
    private String secretKey;
    private String bucketName;
    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getBucketName() { return bucketName; }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

}
