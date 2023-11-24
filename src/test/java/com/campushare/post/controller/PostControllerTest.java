package com.campushare.post.controller;

import com.campushare.post.kafka.PostProducer;
import com.campushare.post.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PostControllerTest {
    @Mock
    private PostService postService;

    @Mock
    private PostProducer postProducer;

    @InjectMocks
    private PostController postController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void something() {
        assert(false);
    }
}
