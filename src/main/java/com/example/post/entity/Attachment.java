package com.example.post.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("attachment")
public class Attachment extends BaseTime {
    @Id
    private Long id;

    @Column("filename")
    public String filename;

    @Column("uuid")
    public String uuid;

    @Column("url")
    public String url;

//    @Column("post_id")
//    private Long post_id;


}
