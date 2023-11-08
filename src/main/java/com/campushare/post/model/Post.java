package com.campushare.post.model;

import com.campushare.post.utils.Status;
import com.campushare.post.utils.Type;

import java.util.Date;
import java.util.List;

public interface Post {
    String getPostId();

    String getUserId();

    String getTitle();

    String getDetails();

    Type getType();

    Integer getNoOfSeats();

    Status getStatus();

    Date getTimestamp();

    List<Comment> getComments();

    void setPostId(String postId);

    void setUserId(String userId);

    void setTitle(String title);

    void setDetails(String details);

    void setType(Type type);

    void setNoOfSeats(Integer noOfSeats);

    void setStatus(Status status);

    void setTimestamp(Date timestamp);

    void setComments(List<Comment> comments);
}
