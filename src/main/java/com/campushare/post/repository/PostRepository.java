package com.campushare.post.repository;

import com.campushare.post.model.Post;
import com.campushare.post.utils.Status;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PostRepository extends MongoRepository<Post,String> {
    List<Post> findByStatusNot(Status status);
    List<Post> findByUserId(String userId);
}
