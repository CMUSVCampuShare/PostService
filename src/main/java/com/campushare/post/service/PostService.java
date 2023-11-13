package com.campushare.post.service;

import com.campushare.post.exception.PostNotFoundException;
import com.campushare.post.factory.PostFactory;
import com.campushare.post.factory.PostFactoryRegistry;
import com.campushare.post.model.Post;
import com.campushare.post.repository.PostRepository;
import com.campushare.post.request.PostRequest;
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

    public Post addPost(PostRequest postRequest) throws IllegalArgumentException, PostNotFoundException {
        if (postRequest == null) {
            throw new IllegalArgumentException("Post cannot be null.");
        }

        PostFactory postFactory = postFactoryRegistry.getPostFactory(postRequest.getType());

        try {
            Post newPost = postFactory.createPost(
                    UUID.randomUUID().toString(),
                    postRequest.getUserId(),
                    postRequest.getTitle(),
                    postRequest.getFrom(),
                    postRequest.getTo(),
                    postRequest.getDetails(),
                    postRequest.getNoOfSeats(),
                    postRequest.getStatus(),
                    postRequest.getTimestamp(),
                    postRequest.getComments()
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

        if (optionalExistingPost.isEmpty()) {
            throw new PostNotFoundException(postId);
        }

        Post existingPost = optionalExistingPost.get();

        return existingPost;
    }

    public Post updatePost(String postId, PostRequest postRequest) throws IllegalArgumentException, PostNotFoundException {
        if (postRequest == null) {
            throw new IllegalArgumentException("Post cannot be null.");
        }

        Optional<Post> optionalExistingPost = postRepository.findById(postId);
        if (optionalExistingPost.isEmpty()) {
            throw new PostNotFoundException(postId);
        }

        Post existingPost = optionalExistingPost.get();

        existingPost.updatePost(postRequest);

        return postRepository.save(existingPost);
    }

    public void deletePost(String postId) throws PostNotFoundException {
        Optional<Post> optionalExistingPost = postRepository.findById(postId);
        if (optionalExistingPost.isEmpty()) {
            throw new PostNotFoundException(postId);
        }

        postRepository.deleteById(postId);
    }

    private boolean isInvalidNoOfSeats(PostRequest postRequest) {
        return postRequest.getNoOfSeats() < 0;
    }
}
