package com.example.post.entity;


import com.example.post.dto.PostDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;



@Table("post")
public class Post extends BaseTime {
    @Id
    private Long id;
    @Column("title")
    public String title;
    @Column("content")
    public String content;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    // 값 조회
    public Post() {
    }

    //Creation Reqeust 가 접근 Entity를 통해 DB를 @POST 할 수 있도록 함수 정의.
    public Post(PostDTO.CreationRequest creationRequest) {
        title = creationRequest.getTitle();
        content = creationRequest.getContent();
    }



}
