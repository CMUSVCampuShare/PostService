package com.campushare.post.controller;

import com.campushare.post.dto.PostDTO;
import com.campushare.post.kafka.PostProducer;
import com.campushare.post.model.Post;
import com.campushare.post.request.PostRequest;
import com.campushare.post.service.PostService;
import com.campushare.post.utils.Status;
import com.campushare.post.utils.Topic;
import com.campushare.post.utils.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PostControllerTest {
    @Mock
    private PostService postService;

    @Mock
    private PostProducer postProducer;

    @InjectMocks
    private PostController postController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createPost_ValidPostRequest_CreatedSuccessfully() {
        PostRequest request = new PostRequest(
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
        Post createdPost = new Post(
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
        PostDTO postDTO = new PostDTO();
        postDTO.setPost(createdPost);

        when(postService.addPost(request)).thenReturn(createdPost);

        ResponseEntity<Post> response = postController.createPost(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdPost, response.getBody());
        verify(postProducer, times(1)).sendMessage(Topic.CREATE, postDTO);
    }

    @Test
    void createPost_ExceptionThrown_InternalServerError() {
        PostRequest request = new PostRequest(
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

        when(postService.addPost(request)).thenThrow(new RuntimeException());

        ResponseEntity<Post> response = postController.createPost(request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void getAllPosts_ReturnsAllPostsSuccessfully() {
        ArrayList<Post> mockPosts = new ArrayList<>();
        Post post = new Post(
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
                Type.RIDE,
                5,
                Status.CANCELED,
                new Date(),
                new ArrayList<>());
        mockPosts.add(post);
        mockPosts.add(post2);
        when(postService.findAllPosts()).thenReturn(mockPosts);

        ResponseEntity<List<Post>> responseEntity = postController.getAllPosts();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockPosts, responseEntity.getBody());
        verify(postService, times(1)).findAllPosts();
    }

    @Test
    void getAllPosts_InternalServerError() {
        when(postService.findAllPosts()).thenThrow(new RuntimeException());

        ResponseEntity<List<Post>> responseEntity = postController.getAllPosts();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    void getAllActivePosts_ReturnsAllActivePostsSuccessfully() {
        List<Post> mockActivePosts = new ArrayList<>();
        Post post = new Post(
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
                Type.RIDE,
                5,
                Status.CANCELED,
                new Date(),
                new ArrayList<>());
        mockActivePosts.add(post);
        mockActivePosts.add(post2);
        when(postService.findAllActivePosts()).thenReturn(mockActivePosts);

        ResponseEntity<List<Post>> responseEntity = postController.getAllActivePosts();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockActivePosts, responseEntity.getBody());
        verify(postService, times(1)).findAllActivePosts();
    }

    @Test
    void getAllActivePosts_InternalServerError() {
        when(postService.findAllActivePosts()).thenThrow(new RuntimeException());

        ResponseEntity<List<Post>> responseEntity = postController.getAllActivePosts();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    void getPost_ReturnsPostSuccessfully() {
        String postId = "postId";
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
        when(postService.findPostByPostId(postId)).thenReturn(mockPost);

        ResponseEntity<Post> responseEntity = postController.getPost(postId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockPost, responseEntity.getBody());
        verify(postService, times(1)).findPostByPostId(postId);
    }

    @Test
    void getPost_InternalServerError() {
        String postId = "postId";
        when(postService.findPostByPostId(postId)).thenThrow(new RuntimeException());

        ResponseEntity<Post> responseEntity = postController.getPost(postId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    void getPostsByUserId_ReturnsPostsSuccessfully() {
        String userId = "userId";
        List<Post> mockPosts = new ArrayList<>();
        Post post = new Post(
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
                "userId",
                "title2",
                "from2",
                "to2",
                "details2",
                Type.RIDE,
                5,
                Status.CANCELED,
                new Date(),
                new ArrayList<>());
        mockPosts.add(post);
        mockPosts.add(post2);
        when(postService.findPostsByUserId(userId)).thenReturn(mockPosts);

        ResponseEntity<List<Post>> responseEntity = postController.getPostsByUserId(userId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockPosts, responseEntity.getBody());
        verify(postService, times(1)).findPostsByUserId(userId);
    }

    @Test
    void getPostsByUserId_InternalServerError() {
        String userId = "userId";
        when(postService.findPostsByUserId(userId)).thenThrow(new RuntimeException());

        ResponseEntity<List<Post>> responseEntity = postController.getPostsByUserId(userId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    void editPost_EditsPostSuccessfully() {
        String postId = "postId";
        PostRequest postRequest = new PostRequest(
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
        Post editedPost = new Post(
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
        when(postService.updatePost(postId, postRequest)).thenReturn(editedPost);

        ResponseEntity<Post> responseEntity = postController.editPost(postId, postRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(editedPost, responseEntity.getBody());
        verify(postService, times(1)).updatePost(postId, postRequest);
        verify(postProducer, times(1)).sendMessage(Topic.EDIT, new PostDTO(editedPost));
    }

    @Test
    void editPost_InternalServerError() {
        String postId = "postId";
        PostRequest postRequest = new PostRequest(
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
        when(postService.updatePost(postId, postRequest)).thenThrow(new RuntimeException());

        ResponseEntity<Post> responseEntity = postController.editPost(postId, postRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    void deletePost_DeletesPostSuccessfully() {
        String postId = "postId";

        ResponseEntity<Void> responseEntity = postController.deletePost(postId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(postService, times(1)).deletePost(postId);
    }

    @Test
    void deletePost_InternalServerError() {
        String postId = "postId";
        doThrow(new RuntimeException()).when(postService).deletePost(postId);

        ResponseEntity<Void> responseEntity = postController.deletePost(postId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }
}
