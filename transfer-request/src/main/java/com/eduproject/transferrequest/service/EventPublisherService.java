package com.eduproject.transferrequest.service;

import com.eduproject.transferrequest.dao.service.OutboxEventService;
import com.eduproject.transferrequest.dto.model.OutboxEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventPublisherService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final OutboxEventService outboxEventService;

    public void publishNextEvent(List<OutboxEventDto> events) {
        OutboxEventDto event = events.stream().findAny().orElse(null);
        if (event == null) {
            return;
        }
        try {
            SendResult<String, String> result = kafkaTemplate.send(
                    event.getTopicName(),
                    event.getPartitionKey(),
                    event.getPayload()
            ).get();
            log.info("Kafka message sent successfully. topic={}, key={}, partition={}, offset={}, eventId={}",
                    result.getRecordMetadata().topic(),
                    event.getPartitionKey(),
                    result.getRecordMetadata().partition(),
                    result.getRecordMetadata().offset(),
                    event.getEventId());
        } catch (ExecutionException e) {
            throw new IllegalStateException("Kafka send interrupted for outbox eventId=" + event.getEventId(), e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Kafka send interrupted for outbox eventId=" + event.getEventId(), e);
        }
        LocalDateTime now = LocalDateTime.now();
        outboxEventService.markAsSent(event.getEventId(), now, now);
        log.info("Successfully sent event eventId: {}, requestId: {}", event.getEventId(), event.getRequestId());
    }

}
