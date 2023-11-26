package com.campushare.post.controller;

import com.campushare.post.model.Comment;
import com.campushare.post.model.Post;
import com.campushare.post.service.CommentService;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(CommentController.class)
@AutoConfigureMockMvc
public class CommentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @MockBean
    private CommentService commentService;

    @Test
    public void testCreateComment() throws Exception {
        String postId = "postId";
        Comment comment = new Comment(
                "commentId",
                postId,
                "This is a comment");
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


        when(postService.findPostByPostId(postId)).thenReturn(post);
        when(commentService.addComment(postId, comment)).thenReturn(comment);

        mockMvc.perform(post("/posts/{postId}/comments", postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(comment)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.commentId").value(comment.getCommentId()))
                .andExpect(jsonPath("$.postId").value(comment.getPostId()))
                .andExpect(jsonPath("$.comment").value(comment.getComment()));
    }

    @Test
    public void testGetAllCommentsByPostId() throws Exception {
        String postId = "postId";
        List<Comment> comments = Arrays.asList(
                new Comment(
                        "commentId1",
                        postId,
                        "This is a comment"),
                new Comment(
                        "commentId2",
                        postId,
                        "This is a comment")
        );

        when(commentService.findCommentsByPostId(postId)).thenReturn(comments);

        mockMvc.perform(get("/posts/{postId}/comments", postId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].commentId").value(comments.get(0).getCommentId()))
                .andExpect(jsonPath("$[1].commentId").value(comments.get(1).getCommentId()));
    }

    private String asJsonString(final Object obj) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
