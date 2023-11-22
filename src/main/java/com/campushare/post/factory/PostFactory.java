package com.campushare.post.factory;

import com.campushare.post.model.Post;
import com.campushare.post.request.PostRequest;
import com.campushare.post.utils.Type;
import org.springframework.stereotype.Component;

@Component
public abstract class PostFactory {

    public abstract Type getType();
    public abstract Post createPost(PostRequest postRequest);

    protected abstract void performNullChecks(PostRequest postRequest);
}
