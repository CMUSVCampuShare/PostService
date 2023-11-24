package com.campushare.post.controller;

import com.campushare.post.dto.PostDTO;
import com.campushare.post.kafka.PostProducer;
import com.campushare.post.model.Post;
import com.campushare.post.request.PostRequest;
import com.campushare.post.service.PostService;
import com.campushare.post.utils.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private PostProducer postProducer;

    @PostMapping("/posts")
    public ResponseEntity<Post> createPost(@RequestBody PostRequest postRequest){
        ResponseEntity<Post> responseEntity;
        try {
            Post createdPost = postService.addPost(postRequest);

            PostDTO postDTO = new PostDTO();
            postDTO.setPost(createdPost);
            postProducer.sendMessage(Topic.CREATE, postDTO);

            responseEntity = new ResponseEntity<>(createdPost, HttpStatus.CREATED);
        } catch (Exception ex) {
            responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getAllPosts(){
        ResponseEntity<List<Post>> responseEntity;
        try {
            List<Post> posts = postService.findAllPosts();
            responseEntity = new ResponseEntity<>(posts, HttpStatus.OK);
        } catch (Exception ex) {
            responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @GetMapping("/posts/active")
    public ResponseEntity<List<Post>> getAllActivePosts(){
        ResponseEntity<List<Post>> responseEntity;
        try {
            List<Post> posts = postService.findAllActivePosts();
            responseEntity = new ResponseEntity<>(posts, HttpStatus.OK);
        } catch (Exception ex) {
            responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<Post> getPost(@PathVariable String postId){
        ResponseEntity<Post> responseEntity;
        try {
            Post post = postService.findPostByPostId(postId);
            responseEntity = new ResponseEntity<>(post, HttpStatus.OK);
        } catch(Exception ex) {
            responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @GetMapping("/users/{userId}/posts")
    public ResponseEntity<List<Post>> getPostsByUserId(@PathVariable String userId){
        ResponseEntity<List<Post>> responseEntity;
        try {
            List<Post> posts = postService.findPostsByUserId(userId);
            responseEntity = new ResponseEntity<>(posts, HttpStatus.OK);
        } catch (Exception ex) {
            responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<Post> editPost(@PathVariable String postId, @RequestBody PostRequest postRequest) {
        ResponseEntity<Post> responseEntity;
        try {
            Post editedPost = postService.updatePost(postId, postRequest);

            PostDTO postDTO = new PostDTO();
            postDTO.setPost(editedPost);
            postProducer.sendMessage(Topic.EDIT, postDTO);

            responseEntity = new ResponseEntity<>(editedPost, HttpStatus.OK);
        } catch(Exception ex) {
            responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return  responseEntity;
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable String postId){
        ResponseEntity<Void> responseEntity = null;
        postService.deletePost(postId);
        responseEntity = new ResponseEntity<>(HttpStatus.OK);
        return responseEntity;
    }
}
