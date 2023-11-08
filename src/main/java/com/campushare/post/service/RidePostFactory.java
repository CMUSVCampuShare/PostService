package com.campushare.post.service;

import com.campushare.post.dto.PostDTO;
import com.campushare.post.model.Comment;
import com.campushare.post.model.Post;
import com.campushare.post.model.RidePost;
import com.campushare.post.request.PostRequest;
import com.campushare.post.utils.Status;
import com.campushare.post.utils.Type;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class RidePostFactory implements PostFactory {
    @Override
    public Post createPost(PostRequest postRequest) {
        String userId = Objects.requireNonNull(postRequest.getUserId(), "User ID cannot be null");
        String title = Objects.requireNonNull(postRequest.getTitle(), "Title cannot be null");
        String details = Objects.requireNonNull(postRequest.getDetails(), "Details cannot be null");
        Type type = Type.RIDE; // If this is a default value, it doesn't need a null check
        Integer noOfSeats = Objects.requireNonNull(postRequest.getNoOfSeats(), "No of seats cannot be null");
        Status status = Objects.requireNonNull(postRequest.getStatus(), "Status cannot be null");
        Date timestamp = Objects.requireNonNull(postRequest.getTimestamp(), "Timestamp cannot be null");
        List<Comment> comments = Objects.requireNonNull(postRequest.getComments(), "Comments cannot be null");

        if (noOfSeats < 0) {
            throw new IllegalArgumentException("No of seats cannot be negative!");
        }

        String postId = UUID.randomUUID().toString();
        return new RidePost(postId, userId, title, details, type, noOfSeats, status, timestamp, comments);
    }
}
