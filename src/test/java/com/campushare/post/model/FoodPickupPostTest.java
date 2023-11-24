package com.campushare.post.model;

import com.campushare.post.request.PostRequest;
import com.campushare.post.utils.Status;
import com.campushare.post.utils.Type;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FoodPickupPostTest {
    @Test
    void updatePost_AllowedFields_SuccessfullyUpdated() {
        FoodPickupPost foodPickupPost = new FoodPickupPost(
                "postId", "userId", "title", "from", "", "details",
                Type.FOODPICKUP, 0, Status.ONGOING, new Date(), Collections.emptyList());

        PostRequest postRequest = new PostRequest();
        postRequest.setTitle("Updated title");
        postRequest.setFrom("Updated from");
        postRequest.setDetails("Updated details");
        postRequest.setStatus(Status.CANCELED);

        foodPickupPost.updatePost(postRequest);

        assertEquals("Updated title", foodPickupPost.getTitle());
        assertEquals("Updated from", foodPickupPost.getFrom());
        assertEquals("Updated details", foodPickupPost.getDetails());
        assertEquals(Status.CANCELED, foodPickupPost.getStatus());
    }

    @Test
    void updatePost_NullFields_NotUpdated() {
        FoodPickupPost foodPickupPost = new FoodPickupPost(
                "postId", "userId", "title", "from", "", "details",
                Type.FOODPICKUP, 0, Status.ONGOING, new Date(), Collections.emptyList());

        PostRequest postRequest = new PostRequest();

        foodPickupPost.updatePost(postRequest);

        assertEquals("title", foodPickupPost.getTitle());
        assertEquals("from", foodPickupPost.getFrom());
        assertEquals("details", foodPickupPost.getDetails());
        assertEquals(Status.ONGOING, foodPickupPost.getStatus());
    }

    @Test
    void updatePost_CommentsUpdated_Success() {
        LunchPost lunchPost = new LunchPost(
                "postId", "userId", "title", "from", "", "details",
                Type.FOODPICKUP, 0, Status.ONGOING, new Date(), Collections.emptyList());

        List<Comment> updatedComments = Arrays.asList(
                new Comment("commentId", "postId", "Updated comment1"),
                new Comment("commentId2", "postId", "Updated comment2"));

        PostRequest postRequest = new PostRequest();
        postRequest.setComments(updatedComments);

        lunchPost.updatePost(postRequest);

        assertEquals(updatedComments, lunchPost.getComments());
    }
}
