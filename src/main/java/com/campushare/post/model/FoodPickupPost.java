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
    public void updatePost(PostRequest postRequest) throws  IllegalArgumentException {
        // cannot change postId, userId, to (always = ""),
        // type (always "FOODPICKUP"), noOfSets (always = 0), timestamp
        if (postRequest.getTitle() != null) {
            this.setTitle(postRequest.getTitle());
        }
        if (postRequest.getFrom() != null) {
            this.setFrom(postRequest.getFrom());
        }
        if (postRequest.getDetails() != null) {
            this.setDetails(postRequest.getDetails());
        }
        if (postRequest.getStatus() != null) {
            this.setStatus(postRequest.getStatus());
        }
        if(postRequest.getComments() != null) {
            this.setComments(postRequest.getComments());
        }
    }
}
