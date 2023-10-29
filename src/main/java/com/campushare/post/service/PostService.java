package com.campushare.post.service;

import com.campushare.post.model.Post;
import com.campushare.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public Post updatePost(String postId, Post post) throws Exception {
        // Check if post is null
        if (post == null) {
            throw new IllegalArgumentException("Post cannot be null.");
        }

        // Check if postRepository.findById(postId) returns a value
        Optional<Post> optionalExistingPost = postRepository.findById(postId);
        if (!optionalExistingPost.isPresent()) {
            throw new Exception("Post with ID " + postId + " not found.");
        }

        Post existingPost = optionalExistingPost.get();

        // Check and update individual properties if they are not null in the incoming post
        if (post.getTitle() != null) {
            existingPost.setTitle(post.getTitle());
        }
        if (post.getDetails() != null) {
            existingPost.setDetails(post.getDetails());
        }
        if (post.getNoOfSeats() != null) {
            existingPost.setNoOfSeats(post.getNoOfSeats());
        }
        if (post.getStatus() != null) {
            existingPost.setStatus(post.getStatus());
        }
        if (post.getComments() != null) {
            existingPost.setComments(post.getComments());
        }

        return postRepository.save(existingPost);
    }

    public void deletePost(String postId) {
        postRepository.deleteById(postId);
    }
}
