package com.eduproject.transferrequest.config.kafka;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Value("${app.kafka.topic.transfer-requested}")
    private String topicName;
    @Value("${app.kafka.topic.transfer-requested.partitions}")
    private int partitions;

    @Bean
    public NewTopic transferRequestedTopic(){
        return TopicBuilder.name(topicName)
                .partitions(partitions)
                .replicas(1)
                .build();
    }
}
