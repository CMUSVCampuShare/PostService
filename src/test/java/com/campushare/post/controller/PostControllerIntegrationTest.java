package com.campushare.post.controller;

import com.campushare.post.kafka.PostProducer;
import com.campushare.post.model.Post;
import com.campushare.post.request.PostRequest;
import com.campushare.post.service.PostService;
import com.campushare.post.utils.Status;
import com.campushare.post.utils.Type;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.matchesPattern;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(PostController.class)
@AutoConfigureMockMvc
public class PostControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @MockBean
    private PostProducer postProducer;

    @Test
    public void testCreatePost() throws Exception {
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

        when(postService.addPost(any(PostRequest.class))).thenReturn(createdPost);

        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(postRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.postId").value(createdPost.getPostId()))
                .andExpect(jsonPath("$.userId").value(createdPost.getUserId()))
                .andExpect(jsonPath("$.title").value(createdPost.getTitle()))
                .andExpect(jsonPath("$.from").value(createdPost.getFrom()))
                .andExpect(jsonPath("$.to").value(createdPost.getTo()))
                .andExpect(jsonPath("$.details").value(createdPost.getDetails()))
                .andExpect(jsonPath("$.type").value(createdPost.getType().toString()))
                .andExpect(jsonPath("$.noOfSeats").value(createdPost.getNoOfSeats()))
                .andExpect(jsonPath("$.status").value(createdPost.getStatus().toString()))
                .andExpect(jsonPath("$.timestamp", matchesPattern("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}(Z|\\+\\d{2}:\\d{2})")))
                .andExpect(jsonPath("$.comments").value(createdPost.getComments()));
    }

    @Test
    public void testGetAllPosts() throws Exception {
        List<Post> posts = Arrays.asList(
                new Post(
                        "postId1",
                        "userId1",
                        "title1",
                        "from1",
                        "to1",
                        "details1",
                        Type.RIDE,
                        2,
                        Status.CREATED,
                        new Date(),
                        new ArrayList<>()),
                new Post(
                        "postId2",
                        "userId2",
                        "title2",
                        "from2",
                        "to2",
                        "details2",
                        Type.LUNCH,
                        3,
                        Status.ONGOING,
                        new Date(),
                        new ArrayList<>())
        );

        when(postService.findAllPosts()).thenReturn(posts);

        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].postId").value(posts.get(0).getPostId()))
                .andExpect(jsonPath("$[1].postId").value(posts.get(1).getPostId()));
    }

    @Test
    public void testGetAllActivePosts() throws Exception {
        List<Post> posts = Arrays.asList(
                new Post(
                        "postId1",
                        "userId1",
                        "title1",
                        "from1",
                        "to1",
                        "details1",
                        Type.RIDE,
                        2,
                        Status.CREATED,
                        new Date(),
                        new ArrayList<>()),
                new Post(
                        "postId2",
                        "userId2",
                        "title2",
                        "from2",
                        "to2",
                        "details2",
                        Type.LUNCH,
                        3,
                        Status.ONGOING,
                        new Date(),
                        new ArrayList<>())
        );

        when(postService.findAllActivePosts()).thenReturn(posts);

        mockMvc.perform(get("/posts/active"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].postId").value(posts.get(0).getPostId()))
                .andExpect(jsonPath("$[1].postId").value(posts.get(1).getPostId()));
    }

    @Test
    public void testGetPostById() throws Exception {
        String postId = "postId";
        Post post = new Post(
                        "postId",
                        "userId",
                        "title",
                        "from",
                        "to",
                        "details",
                        Type.RIDE,
                        2,
                        Status.CREATED,
                        new Date(),
                        new ArrayList<>());

        when(postService.findPostByPostId(postId)).thenReturn(post);

        mockMvc.perform(get("/posts/{postId}", "postId"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postId").value(postId));
    }

    @Test
    public void testGetPostsByUserId() throws Exception {
        String userId = "userId";
        List<Post> posts = Arrays.asList(
                new Post(
                        "postId1",
                        "userId",
                        "title1",
                        "from1",
                        "to1",
                        "details1",
                        Type.RIDE,
                        2,
                        Status.CREATED,
                        new Date(),
                        new ArrayList<>()),
                new Post(
                        "postId2",
                        "userId",
                        "title2",
                        "from2",
                        "to2",
                        "details2",
                        Type.LUNCH,
                        3,
                        Status.ONGOING,
                        new Date(),
                        new ArrayList<>())
        );

        when(postService.findPostsByUserId(userId)).thenReturn(posts);

        mockMvc.perform(get("/users/{userId}/posts", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].postId").value(posts.get(0).getPostId()))
                .andExpect(jsonPath("$[1].postId").value(posts.get(1).getPostId()));
    }

    @Test
    public void testEditPost() throws Exception {
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
                "here",
                "there",
                "details",
                Type.RIDE,
                5,
                Status.CREATED,
                new Date(),
                new ArrayList<>());

        when(postService.updatePost(postId, postRequest)).thenReturn(editedPost);

        mockMvc.perform(put("/posts/{postId}", postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(postRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postId").value(editedPost.getPostId()))
                .andExpect(jsonPath("$.userId").value(editedPost.getUserId()))
                .andExpect(jsonPath("$.title").value(editedPost.getTitle()))
                .andExpect(jsonPath("$.from").value(editedPost.getFrom()))
                .andExpect(jsonPath("$.to").value(editedPost.getTo()))
                .andExpect(jsonPath("$.details").value(editedPost.getDetails()))
                .andExpect(jsonPath("$.type").value(editedPost.getType().toString()))
                .andExpect(jsonPath("$.noOfSeats").value(editedPost.getNoOfSeats()))
                .andExpect(jsonPath("$.status").value(editedPost.getStatus().toString()))
                .andExpect(jsonPath("$.timestamp", matchesPattern("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}(Z|\\+\\d{2}:\\d{2})")))
                .andExpect(jsonPath("$.comments").value(editedPost.getComments()));
    }

    @Test
    public void testDeletePost() throws Exception {
        String postId = "post123";

        mockMvc.perform(delete("/posts/{postId}", postId))
                .andExpect(status().isOk());
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
