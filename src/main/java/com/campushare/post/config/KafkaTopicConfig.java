package com.campushare.post.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Value("${spring.kafka.topic.create-post-topic-name}")
    private String createPostTopicName;

    @Value("${spring.kafka.topic.edit-post-topic-name}")
    private String editPostTopicName;

    @Bean
    public NewTopic createPostTopic() {
        return TopicBuilder
                .name(createPostTopicName)
                .build();
    }

    @Bean
    public NewTopic editPostTopic() {
        return TopicBuilder
                .name(editPostTopicName)
                .build();
    }
}
