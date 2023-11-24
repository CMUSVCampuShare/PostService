package com.campushare.post.factory;

import com.campushare.post.model.FoodPickupPost;
import com.campushare.post.model.Post;
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

public class FoodPickupPostFactoryTest {
    private FoodPickupPostFactory foodPickupPostFactory;

    @BeforeEach
    void setUp() {
        List<PostFactory> factories = Arrays.asList(new RidePostFactory(), new LunchPostFactory(), new FoodPickupPostFactory());
        Map<Type, PostFactory> factoryMap = new HashMap<>();
        factories.forEach(factory -> factoryMap.put(factory.getType(), factory));

        foodPickupPostFactory = new FoodPickupPostFactory();
    }

    @Test
    void createPost_ValidPostRequest_CreatesLunchPost() {
        PostRequest validRequest = new PostRequest();
        validRequest.setPostId("postId");
        validRequest.setUserId("userId");
        validRequest.setTitle("title");
        validRequest.setFrom("from");
        validRequest.setDetails("details");
        validRequest.setStatus(Status.CREATED);

        Post createdPost = foodPickupPostFactory.createPost(validRequest);

        assertTrue(createdPost instanceof FoodPickupPost);
        assertEquals("postId", createdPost.getPostId());
        assertEquals("userId", createdPost.getUserId());
        assertEquals("title", createdPost.getTitle());
        assertEquals("from", createdPost.getFrom());
        assertEquals("details", createdPost.getDetails());
        assertEquals(Status.CREATED, createdPost.getStatus());
    }

    @Test
    void performNullChecks_NullPostRequest_ThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> foodPickupPostFactory.performNullChecks(null));
    }

    @Test
    void performNullChecks_NullPostId_ThrowsIllegalArgumentException() {
        PostRequest nullPostIdRequest = new PostRequest();
        assertThrows(IllegalArgumentException.class, () -> foodPickupPostFactory.performNullChecks(nullPostIdRequest));
    }

    @Test
    void performNullChecks_NullUserId_ThrowsIllegalArgumentException() {
        PostRequest nullUserIdRequest = new PostRequest();
        nullUserIdRequest.setPostId("postId");
        assertThrows(IllegalArgumentException.class, () -> foodPickupPostFactory.performNullChecks(nullUserIdRequest));
    }

    @Test
    void performNullChecks_NullTitle_ThrowsIllegalArgumentException() {
        PostRequest nullTitleRequest = new PostRequest();
        nullTitleRequest.setPostId("postId");
        nullTitleRequest.setUserId("userId");
        assertThrows(IllegalArgumentException.class, () -> foodPickupPostFactory.performNullChecks(nullTitleRequest));
    }

    @Test
    void performNullChecks_NullFrom_ThrowsIllegalArgumentException() {
        PostRequest nullFromRequest = new PostRequest();
        nullFromRequest.setPostId("postId");
        nullFromRequest.setUserId("userId");
        nullFromRequest.setTitle("title");
        assertThrows(IllegalArgumentException.class, () -> foodPickupPostFactory.performNullChecks(nullFromRequest));
    }

    @Test
    void performNullChecks_NullDetails_ThrowsIllegalArgumentException() {
        PostRequest nullDetailsRequest = new PostRequest();
        nullDetailsRequest.setPostId("postId");
        nullDetailsRequest.setUserId("userId");
        nullDetailsRequest.setTitle("title");
        nullDetailsRequest.setFrom("from");
        assertThrows(IllegalArgumentException.class, () -> foodPickupPostFactory.performNullChecks(nullDetailsRequest));
    }
    @Test
    void performNullChecks_NullStatus_ThrowsIllegalArgumentException() {
        PostRequest nullStatusRequest = new PostRequest();
        nullStatusRequest.setPostId("postId");
        nullStatusRequest.setUserId("userId");
        nullStatusRequest.setTitle("title");
        nullStatusRequest.setFrom("from");
        nullStatusRequest.setDetails("details");
        assertThrows(IllegalArgumentException.class, () -> foodPickupPostFactory.performNullChecks(nullStatusRequest));
    }
}
