package com.campushare.post.model;

import com.campushare.post.utils.Status;
import com.campushare.post.utils.Type;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "posts")
@Data
public class Post {
    @Id
    private String postId;
    private String userId;
    private String title;
    private String from;
    private String to;
    private String details;
    private Type type;
    private Integer noOfSeats;
    private Status status;
    private Date timestamp;
    private List<Comment> comments;

    public Post() {}

    public Post(String postId, String userId, String title, String from, String to, String details, Type type, Integer noOfSeats, Status status, Date timestamp, List<Comment> comments) throws IllegalArgumentException {
        this.postId = postId;
        this.userId = userId;
        this.title = title;
        this.from = from;
        this.to = to;
        this.details = details;
        this.type = type;
        this.noOfSeats = noOfSeats;
        this.status = status;
        this.timestamp = timestamp;
        this.comments = comments;
        performNullChecks();
    }

    protected void performNullChecks() throws IllegalArgumentException {
        // ...
    }
}
