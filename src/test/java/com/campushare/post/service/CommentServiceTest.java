package com.campushare.post.service;

import com.campushare.post.model.Comment;
import com.campushare.post.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CommentServiceTest {
    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addComment_SuccessfullyAddsComment() {
        String postId = "postId";
        Comment mockComment = new Comment(
                "commentId",
                "postId",
                "comment");
        when(commentRepository.save(mockComment)).thenReturn(mockComment);

        Comment addedComment = commentService.addComment(postId, mockComment);

        assertNotNull(addedComment.getCommentId());
        assertEquals(postId, addedComment.getPostId());
        verify(commentRepository, times(1)).save(mockComment);
    }

    @Test
    void addComment_NullCommentThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> commentService.addComment("postId", null));
        verify(commentRepository, never()).save(any());
    }

    @Test
    void findCommentsByPostId_ReturnsComments() {
        String postId = "post123";
        List<Comment> mockComments = Collections.singletonList(new Comment());
        when(commentRepository.findByPostId(postId)).thenReturn(mockComments);

        List<Comment> comments = commentService.findCommentsByPostId(postId);

        assertEquals(mockComments, comments);
        verify(commentRepository, times(1)).findByPostId(postId);
    }
}
