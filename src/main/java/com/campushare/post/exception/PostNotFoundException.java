package com.campushare.post.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PostNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private String postId;

    public PostNotFoundException(String postId) {
        super(String.format("Post with ID %s not found.", postId));
        this.postId = postId;
    }

}
