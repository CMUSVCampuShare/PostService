package com.campushare.post.controller;

import com.campushare.post.dto.PostDTO;
import com.campushare.post.kafka.PostProducer;
import com.campushare.post.model.Post;
import com.campushare.post.service.PostService;
import com.campushare.post.utils.Topic;
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

        PostDTO postDTO = new PostDTO();
        postDTO.setPost(createdPost);
        postProducer.sendMessage(Topic.CREATE, postDTO);

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
    public Post editPost(@PathVariable String postId, @RequestBody Post post) throws Exception {
        Post editedPost = postService.updatePost(postId, post);

        PostDTO postDTO = new PostDTO();
        postDTO.setPost(editedPost);
        postProducer.sendMessage(Topic.EDIT, postDTO);

        return editedPost;
    }

    @DeleteMapping("/posts/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePost(@RequestBody String postId){
        postService.deletePost(postId);
    }
}
