package com.campushare.post.utils;

import com.campushare.post.model.Comment;
import com.campushare.post.model.Post;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;


@Component
public abstract class PostFactory {
    public abstract Post createPost(String userId, String title, String details, Type type, Integer noOfSeats, Status status, Date timestamp, List<Comment> comments);
}
