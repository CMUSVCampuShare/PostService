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
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RidePostTest {
    @Test
    void updatePost_AllFieldsUpdated_Success() {
        RidePost ridePost = new RidePost(
                "postId", "userId", "title", "from", "to", "details",
                Type.RIDE, 2, Status.ONGOING, new Date(), Collections.emptyList());

        PostRequest postRequest = new PostRequest();
        postRequest.setTitle("Updated title");
        postRequest.setFrom("Updated from");
        postRequest.setTo("Updated to");
        postRequest.setDetails("Updated details");
        postRequest.setNoOfSeats(4);
        postRequest.setStatus(Status.CANCELED);

        ridePost.updatePost(postRequest);

        assertEquals("Updated title", ridePost.getTitle());
        assertEquals("Updated from", ridePost.getFrom());
        assertEquals("Updated to", ridePost.getTo());
        assertEquals("Updated details", ridePost.getDetails());
        assertEquals(4, ridePost.getNoOfSeats());
        assertEquals(Status.CANCELED, ridePost.getStatus());
    }

    @Test
    void updatePost_NullFields_NotUpdated() {
        RidePost ridePost = new RidePost(
                "postId", "userId", "title", "from", "to", "details",
                Type.RIDE, 2, Status.ONGOING, new Date(), Collections.emptyList());

        PostRequest postRequest = new PostRequest();

        ridePost.updatePost(postRequest);

        assertEquals("title", ridePost.getTitle());
        assertEquals("from", ridePost.getFrom());
        assertEquals("to", ridePost.getTo());
        assertEquals("details", ridePost.getDetails());
        assertEquals(2, ridePost.getNoOfSeats());
        assertEquals(Status.ONGOING, ridePost.getStatus());
    }

    @Test
    void updatePost_NonPositiveSeats_ThrowsIllegalArgumentException() {
        RidePost ridePost = new RidePost(
                "postId", "userId", "title", "from", "to", "details",
                Type.RIDE, 2, Status.CREATED, new Date(), Collections.emptyList());

        PostRequest postRequest = new PostRequest();
        postRequest.setNoOfSeats(0);

        assertThrows(IllegalArgumentException.class, () -> ridePost.updatePost(postRequest));
    }

    @Test
    void updatePost_CommentsUpdated_Success() {
        RidePost ridePost = new RidePost(
                "postId", "userId", "title", "from", "to", "details",
                Type.RIDE, 2, Status.ONGOING, new Date(), Collections.emptyList());

        List<Comment> updatedComments = Arrays.asList(
                new Comment("commentId", "postId", "Updated comment1"),
                new Comment("commentId2", "postId", "Updated comment2"));

        PostRequest postRequest = new PostRequest();
        postRequest.setComments(updatedComments);

        ridePost.updatePost(postRequest);

        assertEquals(updatedComments, ridePost.getComments());
    }
}
