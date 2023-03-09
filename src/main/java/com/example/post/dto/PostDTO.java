package com.example.post.dto;

import com.example.post.entity.Post;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * INNER CLASS
 */
public class PostDTO {
    private String title;
    private String content;

    public PostDTO() {}

//    public PostDTO(int id, String title, String content) {
//        this.title = title;
//        this.content = content;
//    }
//    public String getTitle() {
//        return title;
//    }
//    public void setTitle(String title) {
//        this.title = title;
//    }


    public static class CreationRequest {
        private String title;
        private String content;
        private final ArrayList<MultipartFile> file = new ArrayList<>();

        public CreationRequest(){}

        public CreationRequest(String title, String content) {
            this.title = title;
            this.content = content;
        }
        public String getTitle(){
            return title;
        }
        public String getContent(){
            return content;
        }

        public List<MultipartFile> getFile() {
            return file;
        }

    }

    public static class Response {
        private Post post;

        public Response(){}

        public Response(Post post){
           this.post = post;
        }
        public Post getPost(){
            return post;
        }
        public void setPost(Post post){
            this.post = post;
        }
    }

}
