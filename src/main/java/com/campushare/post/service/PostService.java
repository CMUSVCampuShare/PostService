package com.campushare.post.service;

import com.campushare.post.exception.PostNotFoundException;
import com.campushare.post.model.Post;
import com.campushare.post.repository.PostRepository;
import com.campushare.post.request.PostRequest;
import com.campushare.post.utils.Status;
import com.campushare.post.utils.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private RidePostFactory ridePostFactory;

    public Post addPost(PostRequest postRequest) throws IllegalArgumentException {
        if (postRequest == null) {
            throw new IllegalArgumentException("PostRequest cannot be null");
        }

        PostFactory postFactory = getPostFactoryForType(postRequest.getType());
        Post post = postFactory.createPost(postRequest);

        return postRepository.save(post);
    }

    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }

    public List<Post> findAllActivePosts() {
        return postRepository.findByStatusNot(Status.CANCELED);
    }

    public Post findPostByPostId(String postId) throws PostNotFoundException {
        System.out.println("POST: " + postRepository.findById(postId));
        Optional<Post> optionalExistingPost = postRepository.findById(postId);

        if (!optionalExistingPost.isPresent()) {
            throw new PostNotFoundException(postId);
        }

        Post existingPost = optionalExistingPost.get();

        return existingPost;
    }

//    public Post updatePost(String postId, PostRequest postRequest) throws IllegalArgumentException, PostNotFoundException {
//        if (postRequest == null) {
//            throw new IllegalArgumentException("PostRequest cannot be null.");
//        }
//
//        Optional<Post> optionalExistingPost = postRepository.findById(postId);
//        if (!optionalExistingPost.isPresent()) {
//            throw new PostNotFoundException(postId);
//        }
//
//        Post existingPost = optionalExistingPost.get();
//
//        if (postRequest.getTitle() != null) {
//            existingPost.setTitle(postRequest.getTitle());
//        }
//        if (postRequest.getDetails() != null) {
//            existingPost.setDetails(postRequest.getDetails());
//        }
//        if (postRequest.getType() != null) {
//            existingPost.setType(postRequest.getType());
//        }
//        if (postRequest.getNoOfSeats() != null) {
//            if(isInvalidNoOfSeats(postRequest)) {
//                throw new IllegalArgumentException("No of seats cannot be negative!");
//            }
//            existingPost.setNoOfSeats(postRequest.getNoOfSeats());
//        }
//        if (postRequest.getStatus() != null) {
//            existingPost.setStatus(postRequest.getStatus());
//        }
//
//        return postRepository.save(existingPost);
//    }
//
//    public void deletePost(String postId) throws PostNotFoundException {
//        System.out.println("POST: " + postRepository.findById(postId));
//        Optional<Post> optionalExistingPost = postRepository.findById(postId);
//        if (!optionalExistingPost.isPresent()) {
//            throw new PostNotFoundException(postId);
//        }
//
//        postRepository.deleteById(postId);
//    }

    private boolean isInvalidNoOfSeats(PostRequest postRequest) {
        return postRequest.getNoOfSeats() < 0;
    }

    private PostFactory getPostFactoryForType(Type type) {
        switch (type) {
            case RIDE:
                return ridePostFactory;
            default:
                throw new IllegalArgumentException("Unsupported post type");
        }
    }
}
