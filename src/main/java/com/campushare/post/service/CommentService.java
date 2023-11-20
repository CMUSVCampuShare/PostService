package com.campushare.post.service;

import com.campushare.post.exception.PostNotFoundException;
import com.campushare.post.model.Comment;
import com.campushare.post.model.Post;
import com.campushare.post.repository.CommentRepository;
import com.campushare.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public Comment addComment(String postId, Comment comment) throws IllegalArgumentException {
        if (comment == null) {
            throw new IllegalArgumentException("Comment cannot be null.");
        }

        comment.setCommentId(UUID.randomUUID().toString());
        comment.setPostId(postId);
        System.out.println("Comment in addComment: " + comment);
        return commentRepository.save(comment);
    }

    public List<Comment> findCommentsByPostId(String postId) {
        return commentRepository.findByPostId(postId);
    }
}
