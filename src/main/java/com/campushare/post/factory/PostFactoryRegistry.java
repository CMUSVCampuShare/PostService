package com.campushare.post.factory;

import com.campushare.post.utils.Type;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PostFactoryRegistry {
    private final Map<Type, PostFactory> postFactories;

    public PostFactoryRegistry(List<PostFactory> postFactories) {
        this.postFactories = new HashMap<>();
        postFactories.forEach(factory -> this.postFactories.put(factory.getType(), factory));
    }

    public PostFactory getPostFactory(Type type) {
        return postFactories.get(type);
    }
}
