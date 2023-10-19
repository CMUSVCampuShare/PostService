package com.campushare.post.controller;

import com.campushare.post.model.Comment;
import com.campushare.post.model.Post;
import com.campushare.post.service.CommentService;
import com.campushare.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {
    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public Comment createComment(@PathVariable String postId, @RequestBody Comment comment){
        Post post = postService.findPostByPostId(postId);
        Comment createdComment = commentService.addComment(postId, comment);
        post.getComments().add(comment);
        postService.updatePost(post);
        return createdComment;
    }

    @GetMapping("/posts/{postId}/comments")
    @ResponseStatus(HttpStatus.OK)
    public List<Comment> getAllCommentsByPost(@PathVariable String postId){
        return commentService.findCommentsByPostId(postId);
    }
}
