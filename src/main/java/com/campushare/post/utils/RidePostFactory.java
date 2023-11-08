package com.campushare.post.utils;

import com.campushare.post.model.Comment;
import com.campushare.post.model.Post;
import com.campushare.post.model.RidePost;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class RidePostFactory extends PostFactory {
    @Override
    public Post createPost(String userId, String title, String details, Type type, Integer noOfSeats,Status status, Date timestamp, List<Comment> comments) {
        return new RidePost(userId, title, details, type, noOfSeats, status, timestamp, comments);
    }
}
