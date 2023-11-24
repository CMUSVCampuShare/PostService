package com.campushare.post.service;

import com.campushare.post.exception.PostNotFoundException;
import com.campushare.post.factory.PostFactory;
import com.campushare.post.factory.PostFactoryRegistry;
import com.campushare.post.model.Post;
import com.campushare.post.repository.PostRepository;
import com.campushare.post.request.PostRequest;
import com.campushare.post.utils.Status;
import com.campushare.post.utils.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PostServiceTest {
    @Mock
    private PostRepository postRepository;

    @Mock
    private PostFactoryRegistry postFactoryRegistry;

    @InjectMocks
    private PostService postService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addPost_ValidRequest_CreatesPost() {
        PostRequest validRequest = new PostRequest(
                "postId",
                "userId",
                "title",
                "from",
                "to",
                "details",
                Type.RIDE,
                5,
                Status.CREATED,
                new Date(),
                new ArrayList<>());

        PostFactory mockFactory = mock(PostFactory.class);
        when(postFactoryRegistry.getPostFactory(Type.RIDE)).thenReturn(mockFactory);

        Post mockPost = new Post(
                "postId",
                "userId",
                "title",
                "from",
                "to",
                "details",
                Type.RIDE,
                5,
                Status.CREATED,
                new Date(),
                new ArrayList<>());
        when(mockFactory.createPost(validRequest)).thenReturn(mockPost);

        when(postRepository.save(any(Post.class))).thenReturn(new Post());

        Post result = postService.addPost(validRequest);

        assertNotNull(result);
        verify(postFactoryRegistry, times(1)).getPostFactory(Type.RIDE);
        verify(mockFactory, times(1)).createPost(validRequest);
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    void addPost_NullPostRequest_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> postService.addPost(null));
        verifyNoInteractions(postFactoryRegistry);
        verifyNoInteractions(postRepository);
    }

    @Test
    void findAllPosts_ReturnsListOfPosts() {
        Post post1 = new Post(
                "postId",
                "userId",
                "title",
                "from",
                "to",
                "details",
                Type.RIDE,
                5,
                Status.CREATED,
                new Date(),
                new ArrayList<>());
        Post post2 = new Post(
                "postId2",
                "userId2",
                "title2",
                "from2",
                "to2",
                "details2",
                Type.LUNCH,
                0,
                Status.CREATED,
                new Date(),
                new ArrayList<>());
        when(postRepository.findAll()).thenReturn(Arrays.asList(post1, post2));

        List<Post> posts = postService.findAllPosts();

        assertEquals(2, posts.size());
        assertTrue(posts.contains(post1));
        assertTrue(posts.contains(post2));
        verify(postRepository, times(1)).findAll();
    }

    @Test
    void findAllActivePosts_ReturnsListOfActivePosts() {
        Post activePost1 = new Post(
                "postId",
                "userId",
                "title",
                "from",
                "to",
                "details",
                Type.RIDE,
                5,
                Status.CREATED,
                new Date(),
                new ArrayList<>());
        Post activePost2 = new Post(
                "postId2",
                "userId2",
                "title2",
                "from2",
                "to2",
                "details2",
                Type.FOODPICKUP,
                0,
                Status.ONGOING,
                new Date(),
                new ArrayList<>());
        Post canceledPost = new Post(
                "postId3",
                "userId3",
                "title3",
                "from3",
                "to3",
                "details3",
                Type.LUNCH,
                2,
                Status.CANCELED,
                new Date(),
                new ArrayList<>());
        when(postRepository.findByStatusNot(Status.CANCELED)).thenReturn(Arrays.asList(activePost1, activePost2));

        List<Post> activePosts = postService.findAllActivePosts();

        assertEquals(2, activePosts.size());
        assertTrue(activePosts.contains(activePost1));
        assertTrue(activePosts.contains(activePost2));
        assertFalse(activePosts.contains(canceledPost));
        verify(postRepository, times(1)).findByStatusNot(Status.CANCELED);
    }

    @Test
    void findPostByPostId_ExistingPostId_ReturnsPost() {
        String postId = "existingPostId";
        Post existingPost = new Post(
                "existingPostId",
                "userId",
                "title",
                "from",
                "to",
                "details",
                Type.RIDE,
                5,
                Status.CREATED,
                new Date(),
                new ArrayList<>());
        when(postRepository.findById(postId)).thenReturn(Optional.of(existingPost));

        Post foundPost = postService.findPostByPostId(postId);

        assertEquals(existingPost, foundPost);
        verify(postRepository, times(1)).findById(postId);
    }

    @Test
    void findPostByPostId_NonExistingPostId_ThrowsPostNotFoundException() {
        String nonExistingPostId = "nonExistingId";
        when(postRepository.findById(nonExistingPostId)).thenReturn(Optional.empty());

        assertThrows(PostNotFoundException.class, () -> postService.findPostByPostId(nonExistingPostId));
        verify(postRepository, times(1)).findById(nonExistingPostId);
    }

    @Test
    void findPostsByUserId_ReturnsListOfUserPosts() {
        String userId = "userid";
        Post userPost1 = new Post(
                "postId",
                "userId",
                "title",
                "from",
                "to",
                "details",
                Type.RIDE,
                5,
                Status.CREATED,
                new Date(),
                new ArrayList<>());
        Post userPost2 = new Post(
                "postId2",
                "userId",
                "title2",
                "from2",
                "to2",
                "details2",
                Type.FOODPICKUP,
                0,
                Status.ONGOING,
                new Date(),
                new ArrayList<>());
        Post otherUserPost = new Post(
                "postId3",
                "otherUserId",
                "title3",
                "from3",
                "to3",
                "details3",
                Type.LUNCH,
                2,
                Status.CANCELED,
                new Date(),
                new ArrayList<>());
        when(postRepository.findByUserId(userId)).thenReturn(Arrays.asList(userPost1, userPost2));

        List<Post> userPosts = postService.findPostsByUserId(userId);

        assertEquals(2, userPosts.size());
        assertTrue(userPosts.contains(userPost1));
        assertTrue(userPosts.contains(userPost2));
        assertFalse(userPosts.contains(otherUserPost));
        verify(postRepository, times(1)).findByUserId(userId);
    }

    @Test
    void updatePost_ExistingPost_SuccessfullyUpdatesPost() {
        String postId = "existingId";
        PostRequest validRequest = new PostRequest(
                "existingPostId",
                "userId",
                "title",
                "from",
                "to",
                "details",
                Type.RIDE,
                5,
                Status.CREATED,
                new Date(),
                new ArrayList<>());
        Post existingPost = new Post(
                "existingPostId",
                "userId",
                "title",
                "from",
                "to",
                "details",
                Type.RIDE,
                5,
                Status.CREATED,
                new Date(),
                new ArrayList<>());
        existingPost.setNoOfSeats(4);
        when(postRepository.findById(postId)).thenReturn(Optional.of(existingPost));
        when(postRepository.save(existingPost)).thenReturn(existingPost);

        Post updatedPost = postService.updatePost(postId, validRequest);

        assertNotNull(updatedPost);
        assertEquals(4, updatedPost.getNoOfSeats());
        verify(postRepository, times(1)).findById(postId);
        verify(postRepository, times(1)).save(existingPost);
    }

    @Test
    void updatePost_NullPostRequest_ThrowsIllegalArgumentException() {
        String postId = "postId";

        assertThrows(IllegalArgumentException.class, () -> postService.updatePost(postId, null));
        verifyNoInteractions(postRepository);
    }

    @Test
    void updatePost_NonExistingPostId_ThrowsPostNotFoundException() {
        String nonExistingPostId = "nonExistingId";
        when(postRepository.findById(nonExistingPostId)).thenReturn(Optional.empty());

        assertThrows(PostNotFoundException.class, () -> postService.updatePost(nonExistingPostId, new PostRequest()));
        verify(postRepository, times(1)).findById(nonExistingPostId);
        verifyNoMoreInteractions(postRepository);
    }

    @Test
    void deletePost_ExistingPostId_SuccessfullyDeletesPost() {
        String postId = "existingId";
        Post existingPost = new Post(
                "existingPostId",
                "userId",
                "title",
                "from",
                "to",
                "details",
                Type.RIDE,
                5,
                Status.CREATED,
                new Date(),
                new ArrayList<>());
        when(postRepository.findById(postId)).thenReturn(Optional.of(existingPost));

        postService.deletePost(postId);

        verify(postRepository, times(1)).findById(postId);
        verify(postRepository, times(1)).deleteById(postId);
    }

    @Test
    void deletePost_NonExistingPostId_ThrowsPostNotFoundException() {
        String nonExistingPostId = "nonExistingId";
        when(postRepository.findById(nonExistingPostId)).thenReturn(Optional.empty());

        assertThrows(PostNotFoundException.class, () -> postService.deletePost(nonExistingPostId));
        verify(postRepository, times(1)).findById(nonExistingPostId);
        verifyNoMoreInteractions(postRepository);
    }
}
