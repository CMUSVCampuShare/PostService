package com.campushare.post.factory;

import com.campushare.post.model.Comment;
import com.campushare.post.model.LunchPost;
import com.campushare.post.model.Post;
import com.campushare.post.request.PostRequest;
import com.campushare.post.utils.Type;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;

@Component
public class LunchPostFactory extends PostFactory {

    @Override
    public Type getType() {
        return Type.LUNCH;
    }

    @Override
    public Post createPost(PostRequest postRequest) throws IllegalArgumentException {
        try {
            performNullChecks(postRequest);
            // type should be set to "LUNCH" enum
            // noOfSeats must be >= 0
            // not compulsory to have a timestamp, we can just create it!
            // while creating posts, the comments will not be present and so just an empty array is created
            return new LunchPost(postRequest.getPostId(), postRequest.getUserId(),
                    postRequest.getTitle(), postRequest.getFrom(), postRequest.getTo(),
                    postRequest.getDetails(), Type.LUNCH, postRequest.getNoOfSeats(), postRequest.getStatus(),
                    postRequest.getTimestamp() == null ? new Date() : postRequest.getTimestamp(),
                    new ArrayList<Comment>());
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    @Override
    public void performNullChecks(PostRequest postRequest) {
        if (postRequest.getPostId() == null) throw new IllegalArgumentException("postId cannot be null");
        if (postRequest.getUserId() == null) throw new IllegalArgumentException("userId cannot be null");
        if (postRequest.getTitle() == null) throw new IllegalArgumentException("title cannot be null");
        if (postRequest.getFrom() == null) throw new IllegalArgumentException("from cannot be null");
        if (postRequest.getTo() == null) throw new IllegalArgumentException("to cannot be null");
        if (postRequest.getDetails() == null) throw new IllegalArgumentException("details cannot be null");
        if (postRequest.getNoOfSeats() == null || isNoOfSeatsNegative(postRequest.getNoOfSeats())) throw new IllegalArgumentException("noOfSeats cannot be null and has to be a non-negative integer!");
        if (postRequest.getStatus() == null) throw new IllegalArgumentException("status cannot be null");
    }

    private boolean isNoOfSeatsNegative(Integer noOfSeats) {
        return noOfSeats < 0;
    }
}
