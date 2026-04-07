package com.eduproject.transferprocessing;

import com.eduproject.transferprocessing.config.TestcontainersConfiguration;
import com.eduproject.transferprocessing.dto.event.TransferRequestedEvent;
import com.eduproject.transferprocessing.mapper.TestMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.kafka.KafkaContainer;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.UUID;
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
@Sql(
        scripts = "/sql/init.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS
)
class TransferRequestedConsumerIT {

    @Autowired
    private TestMapper testMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private KafkaContainer kafkaContainer;

    @Value("${app.kafka.topic.transfer-requested}")
    private String topicName;

    private KafkaProducer<String, String> producer;

    @AfterEach
    void tearDown() {
        if (producer != null) {
            producer.close();
        }
    }

    @Test
    void shouldConsumeEventAndPersistProcessedResult() throws Exception {
        TransferRequestedEvent event = buildValidEvent(
                UUID.randomUUID().toString(),
                "req-1001"
        );

        String payload = objectMapper.writeValueAsString(event);

        producer = createProducer();
        producer.send(new ProducerRecord<>(topicName, event.getRequestId(), payload)).get();

        waitUntil(
                () -> processedEventExists(event.getEventId()) && processingResultExists(event.getEventId()),
                Duration.ofSeconds(15)
        );
        Integer processedEventCount = testMapper.countProcessedEventsWithEventId(event.getEventId());

        String processingStatus = testMapper.getTransferProcessingResultsByEventId(event.getEventId()).getProcessingStatus();

        assertEquals(1, processedEventCount);
        assertEquals("COMPLETED", processingStatus);
    }

    @Test
    void shouldIgnoreDuplicateEvent() throws Exception {
        TransferRequestedEvent event = buildValidEvent(
                UUID.randomUUID().toString(),
                "req-2001"
        );

        String payload = objectMapper.writeValueAsString(event);

        producer = createProducer();

        producer.send(new ProducerRecord<>(topicName, event.getRequestId(), payload)).get();
        producer.send(new ProducerRecord<>(topicName, event.getRequestId(), payload)).get();

        waitUntil(
                () -> processedEventExists(event.getEventId()) && processingResultExists(event.getEventId()),
                Duration.ofSeconds(15)
        );
        Integer processedEventCount = testMapper.countProcessedEventsWithEventId(event.getEventId());
        Integer resultCount = testMapper.countTransferProcessingResultsWithEventId(event.getEventId());

        assertEquals(1, processedEventCount);
        assertEquals(1, resultCount);
    }

    private TransferRequestedEvent buildValidEvent(String eventId, String requestId) {
        TransferRequestedEvent event = new TransferRequestedEvent();
        event.setEventId(eventId);
        event.setRequestId(requestId);
        event.setFromAccount("ACC-001");
        event.setToAccount("ACC-002");
        event.setAmount(new BigDecimal("1500.00"));
        event.setCurrency("RUB");
        event.setCreatedAt(LocalDateTime.now());
        return event;
    }

    private KafkaProducer<String, String> createProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaContainer.getBootstrapServers());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        return new KafkaProducer<>(props);
    }

    private boolean processedEventExists(String eventId) {
        Integer count = testMapper.countProcessedEventsWithEventId(eventId);
        return count != null && count > 0;
    }

    private boolean processingResultExists(String eventId) {
        Integer count = testMapper.countTransferProcessingResultsWithEventId(eventId);
        return count != null && count > 0;
    }

    private void waitUntil(BooleanSupplier condition, Duration timeout) throws InterruptedException {
        long deadline = System.currentTimeMillis() + timeout.toMillis();

        while (System.currentTimeMillis() < deadline) {
            if (condition.getAsBoolean()) {
                return;
            }
            Thread.sleep(200);
        }

        fail("Condition was not met within " + timeout.getSeconds() + " seconds");
    }
}