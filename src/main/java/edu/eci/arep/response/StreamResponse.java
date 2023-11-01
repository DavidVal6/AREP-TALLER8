package edu.eci.arep.response;

import edu.eci.arep.model.Post;

public class StreamResponse {
    private String author;
    private String post;

    public StreamResponse(){

    }

    public StreamResponse(Post post) {
        this.author = post.getAuthor();
        this.post = post.getMessage();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
    
}
