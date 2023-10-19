package com.campushare.post.repository;

import com.campushare.post.model.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment,String> {
    List<Comment> findByPostId(String postId);
}
