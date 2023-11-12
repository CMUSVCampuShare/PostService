package com.campushare.post.factory;

import com.campushare.post.model.Comment;
import com.campushare.post.model.Post;
import com.campushare.post.model.RidePost;
import com.campushare.post.utils.Status;
import com.campushare.post.utils.Type;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class RidePostFactory extends PostFactory {

    @Override
    public Type getType() {
        return Type.RIDE;
    }
    @Override
    public Post createPost(String postId, String userId, String title, String from, String to, String details, Integer noOfSeats, Status status, Date timestamp, List<Comment> comments) throws IllegalArgumentException {
        try {
            return new RidePost(postId, userId, title, from, to, details, Type.RIDE, noOfSeats, status, timestamp, comments);
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }
}
