package com.campushare.post.controller;

import com.campushare.post.dto.PostEvent;
import com.campushare.post.kafka.PostProducer;
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

    @Autowired
    private PostProducer postProducer;

    @PostMapping("/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public Post createPost(@RequestBody Post post){
        Post createdPost = postService.addPost(post);

        PostEvent postEvent = new PostEvent();
        postEvent.setStatus("CREATED");
        postEvent.setMessage("Post has been created");
        postEvent.setPost(createdPost);
        postProducer.sendMessage(postEvent);

        return createdPost;
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

    @PutMapping("/posts/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public Post editPost(@RequestBody Post post){
        return postService.updatePost(post);
    }
}
