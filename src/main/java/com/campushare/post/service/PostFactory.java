package com.campushare.post.service;

import com.campushare.post.dto.PostDTO;
import com.campushare.post.model.Post;
import com.campushare.post.request.PostRequest;

public interface PostFactory {
    Post createPost(PostRequest postRequest);
}
