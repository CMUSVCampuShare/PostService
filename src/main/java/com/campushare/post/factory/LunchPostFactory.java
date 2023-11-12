package com.campushare.post.factory;

import com.campushare.post.model.Comment;
import com.campushare.post.model.LunchPost;
import com.campushare.post.model.Post;
import com.campushare.post.utils.Status;
import com.campushare.post.utils.Type;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class LunchPostFactory extends PostFactory {

    @Override
    public Type getType() {
        return Type.LUNCH;
    }

    @Override
    public Post createPost(String postId, String userId, String title, String from, String to, String details, Integer noOfSeats, Status status, Date timestamp, List<Comment> comments) {
        return new LunchPost(postId, userId, title, from, to, details, Type.LUNCH, noOfSeats, status, timestamp, comments);
    }
}
