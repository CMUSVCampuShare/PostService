package com.campushare.post.request;

import com.campushare.post.model.Comment;
import com.campushare.post.model.Post;
import com.campushare.post.utils.Status;
import com.campushare.post.utils.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
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

    public void setAttributesFromPost(Post post) {
        this.postId = post.getPostId();
        this.userId = post.getUserId();
        this.title = post.getTitle();
        this.from = post.getFrom();
        this.to = post.getTo();
        this.details = post.getDetails();
        this.type = post.getType();
        this.noOfSeats = post.getNoOfSeats();
        this.status = post.getStatus();
        this.timestamp = post.getTimestamp();
        this.comments = post.getComments();
    }
}
