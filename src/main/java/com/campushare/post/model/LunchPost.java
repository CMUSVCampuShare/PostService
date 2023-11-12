package com.campushare.post.model;

import com.campushare.post.utils.Status;
import com.campushare.post.utils.Type;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "posts")
public class LunchPost extends Post {
    public LunchPost(String postId, String userId, String title, String details, Type type, Integer noOfSeats, Status status, Date timestamp, List<Comment> comments) {
        super(postId, userId, title, details,type, noOfSeats, status, timestamp, comments);
    }
}
