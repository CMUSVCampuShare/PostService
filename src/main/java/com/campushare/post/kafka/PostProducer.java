package com.campushare.post.kafka;

import com.campushare.post.dto.PostDTO;
import com.campushare.post.utils.Topic;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class PostProducer {

    private static final Logger LOGGER = Logger.getLogger(PostProducer.class);

    @Autowired
    private NewTopic createPostTopicName;

    @Autowired
    private NewTopic editPostTopicName;

    @Autowired
    private KafkaTemplate<String, PostDTO> kafkaTemplate;

    public void sendMessage(Topic topic, PostDTO dto) {
        LOGGER.info(String.format("Post event => %s", dto.toString()));
        Message<PostDTO> message;
        if (topic == Topic.CREATE) {
            message = MessageBuilder
                    .withPayload(dto)
                    .setHeader(KafkaHeaders.TOPIC, createPostTopicName.name())
                    .build();
        } else {
            message = MessageBuilder
                    .withPayload(dto)
                    .setHeader(KafkaHeaders.TOPIC, editPostTopicName.name())
                    .build();
        }
        kafkaTemplate.send(message);
    }
}
