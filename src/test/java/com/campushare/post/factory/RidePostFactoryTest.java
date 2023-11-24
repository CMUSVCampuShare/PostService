package com.campushare.post.factory;

import com.campushare.post.model.Post;
import com.campushare.post.model.RidePost;
import com.campushare.post.request.PostRequest;
import com.campushare.post.utils.Status;
import com.campushare.post.utils.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class RidePostFactoryTest {

    private RidePostFactory ridePostFactory;

    @BeforeEach
    void setUp() {
        List<PostFactory> factories = Arrays.asList(new RidePostFactory(), new LunchPostFactory(), new FoodPickupPostFactory());
        Map<Type, PostFactory> factoryMap = new HashMap<>();
        factories.forEach(factory -> factoryMap.put(factory.getType(), factory));

        ridePostFactory = new RidePostFactory();
    }

    @Test
    void createPost_ValidPostRequest_CreatesRidePost() {
        PostRequest validRequest = new PostRequest();
        validRequest.setPostId("postId");
        validRequest.setUserId("userId");
        validRequest.setTitle("title");
        validRequest.setFrom("from");
        validRequest.setTo("to");
        validRequest.setDetails("details");
        validRequest.setNoOfSeats(2);
        validRequest.setStatus(Status.CREATED);

        Post createdPost = ridePostFactory.createPost(validRequest);

        assertTrue(createdPost instanceof RidePost);
        assertEquals("postId", createdPost.getPostId());
        assertEquals("userId", createdPost.getUserId());
        assertEquals("title", createdPost.getTitle());
        assertEquals("from", createdPost.getFrom());
        assertEquals("to", createdPost.getTo());
        assertEquals("details", createdPost.getDetails());
        assertEquals(2, createdPost.getNoOfSeats());
        assertEquals(Status.CREATED, createdPost.getStatus());
    }

    @Test
    void performNullChecks_NullPostRequest_ThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ridePostFactory.performNullChecks(null));
    }

    @Test
    void performNullChecks_NullPostId_ThrowsIllegalArgumentException() {
        PostRequest nullPostIdRequest = new PostRequest();
        assertThrows(IllegalArgumentException.class, () -> ridePostFactory.performNullChecks(nullPostIdRequest));
    }

    @Test
    void performNullChecks_NullUserId_ThrowsIllegalArgumentException() {
        PostRequest nullUserIdRequest = new PostRequest();
        nullUserIdRequest.setPostId("postId");
        assertThrows(IllegalArgumentException.class, () -> ridePostFactory.performNullChecks(nullUserIdRequest));
    }

    @Test
    void performNullChecks_NullTitle_ThrowsIllegalArgumentException() {
        PostRequest nullTitleRequest = new PostRequest();
        nullTitleRequest.setPostId("postId");
        nullTitleRequest.setUserId("userId");
        assertThrows(IllegalArgumentException.class, () -> ridePostFactory.performNullChecks(nullTitleRequest));
    }

    @Test
    void performNullChecks_NullFrom_ThrowsIllegalArgumentException() {
        PostRequest nullFromRequest = new PostRequest();
        nullFromRequest.setPostId("postId");
        nullFromRequest.setUserId("userId");
        nullFromRequest.setTitle("title");
        assertThrows(IllegalArgumentException.class, () -> ridePostFactory.performNullChecks(nullFromRequest));
    }

    @Test
    void performNullChecks_NullTo_ThrowsIllegalArgumentException() {
        PostRequest nullToRequest = new PostRequest();
        nullToRequest.setPostId("postId");
        nullToRequest.setUserId("userId");
        nullToRequest.setTitle("title");
        nullToRequest.setFrom("from");
        assertThrows(IllegalArgumentException.class, () -> ridePostFactory.performNullChecks(nullToRequest));
    }

    @Test
    void performNullChecks_NullDetails_ThrowsIllegalArgumentException() {
        PostRequest nullDetailsRequest = new PostRequest();
        nullDetailsRequest.setPostId("postId");
        nullDetailsRequest.setUserId("userId");
        nullDetailsRequest.setTitle("title");
        nullDetailsRequest.setFrom("from");
        nullDetailsRequest.setTo("to");
        assertThrows(IllegalArgumentException.class, () -> ridePostFactory.performNullChecks(nullDetailsRequest));
    }

    @Test
    void performNullChecks_NullNoOfSeats_ThrowsIllegalArgumentException() {
        PostRequest nullNoOfSeatsRequest = new PostRequest();
        nullNoOfSeatsRequest.setPostId("postId");
        nullNoOfSeatsRequest.setUserId("userId");
        nullNoOfSeatsRequest.setTitle("title");
        nullNoOfSeatsRequest.setFrom("from");
        nullNoOfSeatsRequest.setTo("to");
        nullNoOfSeatsRequest.setDetails("details");
        assertThrows(IllegalArgumentException.class, () -> ridePostFactory.performNullChecks(nullNoOfSeatsRequest));
    }

    @Test
    void performNullChecks_InvalidNoOfSeats_ThrowsIllegalArgumentException() {
        PostRequest invalidNoOfSeatsRequest = new PostRequest();
        invalidNoOfSeatsRequest.setPostId("postId");
        invalidNoOfSeatsRequest.setUserId("userId");
        invalidNoOfSeatsRequest.setTitle("title");
        invalidNoOfSeatsRequest.setFrom("from");
        invalidNoOfSeatsRequest.setTo("to");
        invalidNoOfSeatsRequest.setDetails("details");
        invalidNoOfSeatsRequest.setNoOfSeats(0);
        assertThrows(IllegalArgumentException.class, () -> ridePostFactory.performNullChecks(invalidNoOfSeatsRequest));
    }

    @Test
    void performNullChecks_NullStatus_ThrowsIllegalArgumentException() {
        PostRequest nullStatusRequest = new PostRequest();
        nullStatusRequest.setPostId("postId");
        nullStatusRequest.setUserId("userId");
        nullStatusRequest.setTitle("title");
        nullStatusRequest.setFrom("from");
        nullStatusRequest.setTo("to");
        nullStatusRequest.setDetails("details");
        nullStatusRequest.setNoOfSeats(2);
        assertThrows(IllegalArgumentException.class, () -> ridePostFactory.performNullChecks(nullStatusRequest));
    }

}
