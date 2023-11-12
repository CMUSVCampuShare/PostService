package com.campushare.post.factory;

import com.campushare.post.model.Comment;
import com.campushare.post.model.FoodPickupPost;
import com.campushare.post.model.Post;
import com.campushare.post.utils.Status;
import com.campushare.post.utils.Type;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class FoodPickupPostFactory extends PostFactory {

    @Override
    public Type getType() {
        return Type.FOODPICKUP;
    }

    @Override
    public Post createPost(String postId, String userId, String title, String details, Integer noOfSeats, Status status, Date timestamp, List<Comment> comments) {
        return new FoodPickupPost(postId, userId, title, details, Type.FOODPICKUP, noOfSeats, status, timestamp, comments);
    }
}
