package com.campushare.post.controller;

import com.campushare.post.model.Comment;
import com.campushare.post.model.Post;
import com.campushare.post.request.PostRequest;
import com.campushare.post.service.CommentService;
import com.campushare.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {
    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<Comment>  createComment(@PathVariable String postId, @RequestBody Comment comment) {
        ResponseEntity<Comment> responseEntity;
        try {
            Post post = postService.findPostByPostId(postId);

            Comment createdComment = commentService.addComment(postId, comment);

            post.getComments().add(comment);

            PostRequest postRequest = new PostRequest();
            postRequest.setAttributesFromPost(post);

            postService.updatePost(postId, postRequest);

            responseEntity = new ResponseEntity<>(createdComment, HttpStatus.CREATED);
        } catch (Exception ex) {
            responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<Comment>> getAllCommentsByPostId(@PathVariable String postId){
        ResponseEntity<List<Comment>> responseEntity;
        try {
            List<Comment> comments = commentService.findCommentsByPostId(postId);
            responseEntity = new ResponseEntity<>(comments, HttpStatus.OK);
        } catch (Exception ex) {
            responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
