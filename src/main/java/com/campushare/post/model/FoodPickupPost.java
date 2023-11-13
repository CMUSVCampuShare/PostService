package com.campushare.post.model;

import com.campushare.post.request.PostRequest;
import com.campushare.post.utils.Status;
import com.campushare.post.utils.Type;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "posts")
public class FoodPickupPost extends Post {
    public FoodPickupPost(String postId, String userId, String title, String from, String to, String details, Type type, Integer noOfSeats, Status status, Date timestamp, List<Comment> comments) throws IllegalArgumentException {
        super(postId, userId, title, from, to, details, type, noOfSeats, status, timestamp, comments);
    }

    @Override
    protected void performNullChecks() throws IllegalArgumentException {
        if (getPostId() == null) throw new IllegalArgumentException("postId cannot be null");
        if (getUserId() == null) throw new IllegalArgumentException("userId cannot be null");
        if (getTitle() == null) throw new IllegalArgumentException("title cannot be null");
        if (getFrom() == null) throw new IllegalArgumentException("from cannot be null");
        if (getDetails() == null) throw new IllegalArgumentException("details cannot be null");
        if (getStatus() == null) throw new IllegalArgumentException("status cannot be null");
        if (getTimestamp() == null) throw new IllegalArgumentException("timestamp cannot be null");
        if (getComments() == null) throw new IllegalArgumentException("comments cannot be null");
    }

    @Override
    public void updatePost(PostRequest postRequest) throws  IllegalArgumentException {
        if (postRequest.getTitle() != null) {
            this.setTitle(postRequest.getTitle());
        }
        if (postRequest.getFrom() != null) {
            this.setFrom(postRequest.getFrom());
        }
        if (postRequest.getTo() != null) {
            this.setTo(postRequest.getTo());
        }
        if (postRequest.getDetails() != null) {
            this.setDetails(postRequest.getDetails());
        }
        if (postRequest.getType() != null) {
            this.setType(postRequest.getType());
        }
        if (postRequest.getNoOfSeats() != null) {
            if(isInvalidNoOfSeats(postRequest.getNoOfSeats())) {
                throw new IllegalArgumentException("No of seats cannot be negative!");
            }
            this.setNoOfSeats(postRequest.getNoOfSeats());
        }
        if (postRequest.getStatus() != null) {
            this.setStatus(postRequest.getStatus());
        }
    }

    private boolean isInvalidNoOfSeats(Integer noOfSeats) {
        return noOfSeats < 0;
    }
}
