package com.campushare.post.controller;

import com.campushare.post.model.Post;
import com.campushare.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public Post createPost(@RequestBody Post post){
        return postService.addPost(post);
    }

    @GetMapping("/posts")
    @ResponseStatus(HttpStatus.OK)
    public List<Post> getAllPosts(){
        return postService.findAllPosts();
    }

    @GetMapping("/posts/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public Post getPost(@PathVariable String postId){
        return postService.findPostByPostId(postId);
    }

    @PutMapping("/posts/:postId")
    @ResponseStatus(HttpStatus.OK)
    public Post editPost(@RequestBody Post post){
        return postService.updatePost(post);
    }
}
