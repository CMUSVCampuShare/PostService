package com.campushare.post.controller;

import com.campushare.post.model.Comment;
import com.campushare.post.model.Post;
import com.campushare.post.service.CommentService;
import com.campushare.post.service.PostService;
import com.campushare.post.utils.Type;
import com.campushare.post.utils.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CommentControllerTest {

    @Mock
    private PostService postService;

    @Mock
    private CommentService commentService;

    @InjectMocks
    private CommentController commentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createComment_ReturnsCreatedComment() {
        String postId = "postId";
        Comment mockComment = new Comment(
                "commentId",
                "postId",
                "comment");
        when(postService.findPostByPostId(postId)).thenReturn(new Post(
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
                new ArrayList<>()));
        when(commentService.addComment(postId, mockComment)).thenReturn(mockComment);

        ResponseEntity<Comment> response = commentController.createComment(postId, mockComment);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(mockComment, response.getBody());
        verify(postService, times(1)).findPostByPostId(postId);
        verify(commentService, times(1)).addComment(postId, mockComment);
    }

    @Test
    void createComment_ReturnsInternalServerError() {
        String postId = "postId";
        Comment mockComment = new Comment(
                "commentId",
                "postId",
                "comment");
        when(postService.findPostByPostId(postId)).thenThrow(new RuntimeException());

        ResponseEntity<Comment> response = commentController.createComment(postId, mockComment);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(postService, times(1)).findPostByPostId(postId);
        verify(commentService, never()).addComment(any(), any());
    }

    @Test
    void getAllCommentsByPostId_ReturnsComments() {
        String postId = "postId";
        List<Comment> mockComments = Collections.singletonList(new Comment(
                "commentId",
                "postId",
                "comment"));
        when(commentService.findCommentsByPostId(postId)).thenReturn(mockComments);

        ResponseEntity<List<Comment>> response = commentController.getAllCommentsByPostId(postId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockComments, response.getBody());
        verify(commentService, times(1)).findCommentsByPostId(postId);
    }

    @Test
    void getAllCommentsByPostId_ReturnsInternalServerError() {
        String postId = "postId";
        when(commentService.findCommentsByPostId(postId)).thenThrow(new RuntimeException());

        ResponseEntity<List<Comment>> response = commentController.getAllCommentsByPostId(postId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(commentService, times(1)).findCommentsByPostId(postId);
    }
}
