package com.campushare.post.service;

import com.campushare.post.exception.PostNotFoundException;
import com.campushare.post.factory.PostFactory;
import com.campushare.post.factory.PostFactoryRegistry;
import com.campushare.post.model.Post;
import com.campushare.post.repository.PostRepository;
import com.campushare.post.utils.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostFactoryRegistry postFactoryRegistry;

    public Post addPost(Post post) throws IllegalArgumentException, PostNotFoundException {
        if (post == null) {
            throw new IllegalArgumentException("Post cannot be null.");
        }

        PostFactory postFactory = postFactoryRegistry.getPostFactory(post.getType());

        try {
            Post newPost = postFactory.createPost(
                    UUID.randomUUID().toString(),
                    post.getUserId(),
                    post.getTitle(),
                    post.getFrom(),
                    post.getTo(),
                    post.getDetails(),
                    post.getNoOfSeats(),
                    post.getStatus(),
                    post.getTimestamp(),
                    post.getComments()
            );

            return postRepository.save(newPost);
        } catch(IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }

    public List<Post> findAllActivePosts() {
        return postRepository.findByStatusNot(Status.CANCELED);
    }

    public Post findPostByPostId(String postId) throws PostNotFoundException {
        Optional<Post> optionalExistingPost = postRepository.findById(postId);

        if (!optionalExistingPost.isPresent()) {
            throw new PostNotFoundException(postId);
        }

        Post existingPost = optionalExistingPost.get();

        return existingPost;
    }

    public Post updatePost(String postId, Post post) throws IllegalArgumentException, PostNotFoundException {
        if (post == null) {
            throw new IllegalArgumentException("Post cannot be null.");
        }

        Optional<Post> optionalExistingPost = postRepository.findById(postId);
        if (!optionalExistingPost.isPresent()) {
            throw new PostNotFoundException(postId);
        }

        Post existingPost = optionalExistingPost.get();

        if (post.getTitle() != null) {
            existingPost.setTitle(post.getTitle());
        }
        if (post.getFrom() != null) {
            existingPost.setFrom(post.getFrom());
        }
        if (post.getTo() != null) {
            existingPost.setTo(post.getTo());
        }
        if (post.getDetails() != null) {
            existingPost.setDetails(post.getDetails());
        }
        if (post.getType() != null) {
            existingPost.setType(post.getType());
        }
        if (post.getNoOfSeats() != null) {
            if(isInvalidNoOfSeats(post)) {
                throw new IllegalArgumentException("No of seats cannot be negative!");
            }
            existingPost.setNoOfSeats(post.getNoOfSeats());
        }
        if (post.getStatus() != null) {
            existingPost.setStatus(post.getStatus());
        }

        return postRepository.save(existingPost);
    }

    public void deletePost(String postId) throws PostNotFoundException {
        Optional<Post> optionalExistingPost = postRepository.findById(postId);
        if (!optionalExistingPost.isPresent()) {
            throw new PostNotFoundException(postId);
        }

        postRepository.deleteById(postId);
    }

    private boolean isInvalidNoOfSeats(Post post) {
        return post.getNoOfSeats() < 0;
    }
}
