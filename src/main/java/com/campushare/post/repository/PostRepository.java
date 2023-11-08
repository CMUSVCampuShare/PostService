package com.campushare.post.repository;

import com.campushare.post.model.Post;
import com.campushare.post.utils.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,String> {
    List<Post> findByStatusNot(Status status);
}
