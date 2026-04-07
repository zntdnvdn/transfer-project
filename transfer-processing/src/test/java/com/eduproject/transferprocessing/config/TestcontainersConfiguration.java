package com.eduproject.transferprocessing.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class TestcontainersConfiguration {

	@Bean
	@ServiceConnection
	KafkaContainer kafkaContainer() {
		return new KafkaContainer(DockerImageName.parse("apache/kafka-native:3.8.0"));
	}

	@Bean
	@ServiceConnection
	PostgreSQLContainer<?> postgresContainer() {
		return new PostgreSQLContainer<>(DockerImageName.parse("postgres:16"))
				.withDatabaseName("transfer_db")
				.withUsername("postgres")
				.withPassword("postgres");
	}
//	@Bean
//	NewTopic transferRequestedTopic(
//			@Value("${app.kafka.topic.transfer-requested}") String topicName
//	) {
//		return TopicBuilder.name(topicName)
//				.partitions(1)
//				.replicas(1)
//				.build();
//	}



}
