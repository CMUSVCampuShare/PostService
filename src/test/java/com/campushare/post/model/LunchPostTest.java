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

public class LunchPostTest {
    @Test
    void updatePost_AllFieldsUpdated_Success() {
        LunchPost lunchPost = new LunchPost(
                "postId", "userId", "title", "from", "to", "details",
                Type.LUNCH, 2, Status.ONGOING, new Date(), Collections.emptyList());

        PostRequest postRequest = new PostRequest();
        postRequest.setTitle("Updated title");
        postRequest.setFrom("Updated from");
        postRequest.setTo("Updated to");
        postRequest.setDetails("Updated details");
        postRequest.setNoOfSeats(4);
        postRequest.setStatus(Status.CANCELED);

        lunchPost.updatePost(postRequest);

        assertEquals("Updated title", lunchPost.getTitle());
        assertEquals("Updated from", lunchPost.getFrom());
        assertEquals("Updated to", lunchPost.getTo());
        assertEquals("Updated details", lunchPost.getDetails());
        assertEquals(4, lunchPost.getNoOfSeats());
        assertEquals(Status.CANCELED, lunchPost.getStatus());
    }

    @Test
    void updatePost_NullFields_NotUpdated() {
        LunchPost lunchPost = new LunchPost(
                "postId", "userId", "title", "from", "to", "details",
                Type.LUNCH, 2, Status.ONGOING, new Date(), Collections.emptyList());

        PostRequest postRequest = new PostRequest();

        lunchPost.updatePost(postRequest);

        assertEquals("title", lunchPost.getTitle());
        assertEquals("from", lunchPost.getFrom());
        assertEquals("to", lunchPost.getTo());
        assertEquals("details", lunchPost.getDetails());
        assertEquals(2, lunchPost.getNoOfSeats());
        assertEquals(Status.ONGOING, lunchPost.getStatus());
    }

    @Test
    void updatePost_NonPositiveSeats_ThrowsIllegalArgumentException() {
        LunchPost lunchPost = new LunchPost(
                "postId", "userId", "title", "from", "to", "details",
                Type.LUNCH, 2, Status.CREATED, new Date(), Collections.emptyList());

        PostRequest postRequest = new PostRequest();
        postRequest.setNoOfSeats(-1);

        assertThrows(IllegalArgumentException.class, () -> lunchPost.updatePost(postRequest));
    }

    @Test
    void updatePost_CommentsUpdated_Success() {
        LunchPost lunchPost = new LunchPost(
                "postId", "userId", "title", "from", "to", "details",
                Type.LUNCH, 2, Status.ONGOING, new Date(), Collections.emptyList());

        List<Comment> updatedComments = Arrays.asList(
                new Comment("commentId", "postId", "Updated comment1"),
                new Comment("commentId2", "postId", "Updated comment2"));

        PostRequest postRequest = new PostRequest();
        postRequest.setComments(updatedComments);

        lunchPost.updatePost(postRequest);

        assertEquals(updatedComments, lunchPost.getComments());
    }
}
