package com.campushare.post.model;

import com.campushare.post.utils.Status;
import com.campushare.post.utils.Type;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@JsonSubTypes({ @JsonSubTypes.Type(RidePost.class)})
@Entity(name = "posts")
@Inheritance(strategy = InheritanceType.JOINED)
@Table(indexes = @Index(columnList = "postId"))
public abstract class Post {

    @Id
    @GeneratedValue
    private String postId;
    private String userId;
    private String title;
    private String details;
    private Type type;
    private Integer noOfSeats;
    private Status status;
    private Date timestamp;
    private List<Comment> comments;

    public Post() {
    }

    public Post(String postId, String userId, String title, String details, Type type, Integer noOfSeats, Status status, Date timestamp, List<Comment> comments) {
        this.postId = postId;
        this.userId = userId;
        this.title = title;
        this.details = details;
        this.type = type;
        this.noOfSeats = noOfSeats;
        this.status = status;
        this.timestamp = timestamp;
        this.comments = comments;
    }

    public String getPostId() {
        return postId;
    }

    public String getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getDetails() {
        return details;
    }

    public Type getType() {
        return type;
    }

    public Integer getNoOfSeats() {
        return noOfSeats;
    }

    public Status getStatus() {
        return status;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setNoOfSeats(Integer noOfSeats) {
        this.noOfSeats = noOfSeats;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
