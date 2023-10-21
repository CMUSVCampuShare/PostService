package com.campushare.post.kafka;

import com.campushare.post.dto.PostEvent;
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
    private NewTopic topic;

    @Autowired
    private KafkaTemplate<String, PostEvent> kafkaTemplate;

//    public PostProducer(NewTopic topic, KafkaTemplate<String, PostEvent> kafkaTemplate) {
//        this.topic = topic;
//        this.kafkaTemplate = kafkaTemplate;
//    }

    public void sendMessage(PostEvent event) {
        LOGGER.info(String.format("Post event => %s", event.toString()));

        Message<PostEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, topic.name())
                .build();
        kafkaTemplate.send(message);
    }
}
