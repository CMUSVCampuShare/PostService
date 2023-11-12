package com.campushare.post.model;

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
    protected void performNullChecks() throws IllegalArgumentException {
        if (getPostId() == null) throw new IllegalArgumentException("postId cannot be null");
        if (getUserId() == null) throw new IllegalArgumentException("userId cannot be null");
        if (getTitle() == null) throw new IllegalArgumentException("title cannot be null");
        if (getFrom() == null) throw new IllegalArgumentException("from cannot be null");
        if (getTo() == null) throw new IllegalArgumentException("to cannot be null");
        if (getDetails() == null) throw new IllegalArgumentException("details cannot be null");
        if (getNoOfSeats() == null || isInvalidNoOfSeats(this)) throw new IllegalArgumentException("noOfSeats cannot be null or negative!");
        if (getStatus() == null) throw new IllegalArgumentException("status cannot be null");
        if (getTimestamp() == null) throw new IllegalArgumentException("timestamp cannot be null");
        if (getComments() == null) throw new IllegalArgumentException("comments cannot be null");
    }

    private boolean isInvalidNoOfSeats(Post post) {
        return post.getNoOfSeats() < 0;
    }
}
