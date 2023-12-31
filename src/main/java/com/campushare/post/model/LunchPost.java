package com.campushare.post.model;

import com.campushare.post.request.PostRequest;
import com.campushare.post.utils.Status;
import com.campushare.post.utils.Type;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "posts")
public class LunchPost extends Post {
    public LunchPost(String postId, String userId, String title, String from, String to, String details, Type type, Integer noOfSeats, Status status, Date timestamp, List<Comment> comments) throws IllegalArgumentException {
        super(postId, userId, title, from, to, details,type, noOfSeats, status, timestamp, comments);
    }

    @Override
    public void updatePost(PostRequest postRequest) throws  IllegalArgumentException {
        // cannot change postId, userId, type (always "LUNCH"),  timestamp
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
        if (postRequest.getNoOfSeats() != null) {
            if(isNoOfSeatsNegative(postRequest.getNoOfSeats())) {
                throw new IllegalArgumentException("noOfSeats has to be a non-negative integer!");
            }
            this.setNoOfSeats(postRequest.getNoOfSeats());
        }
        if (postRequest.getStatus() != null) {
            this.setStatus(postRequest.getStatus());
        }
        if(postRequest.getComments() != null) {
            this.setComments(postRequest.getComments());
        }
    }

    private boolean isNoOfSeatsNegative(Integer noOfSeats) {
        return noOfSeats < 0;
    }
}
