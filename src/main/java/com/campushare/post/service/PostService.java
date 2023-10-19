package com.campushare.post.service;

import com.campushare.post.model.Post;
import com.campushare.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post addPost(Post post) {
        post.setPostId(UUID.randomUUID().toString().split("-")[0]);
        return postRepository.save(post);
    }

    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }

    public Post findPostByPostId(String postId) {
        return postRepository.findById(postId).get();
    }

    public Post updatePost(Post postRequest) {
        Post existingPost = postRepository.findById(postRequest.getPostId()).get();
        existingPost.setTitle(postRequest.getTitle());
        existingPost.setDetails(postRequest.getDetails());
        existingPost.setNoOfSeats(postRequest.getNoOfSeats());
        existingPost.setStatus(postRequest.getStatus());
        existingPost.setComments(postRequest.getComments());
        return postRepository.save(existingPost);
    }
}
