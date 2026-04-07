package com.eduproject.transferrequest.service.factory;

import com.eduproject.transferrequest.config.kafka.KafkaTopicConfig;
import com.eduproject.transferrequest.dto.model.OutboxEventDto;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class OutboxEventFactory {
    private final NewTopic transferRequestedTopic;
    public  OutboxEventDto createOutboxEventDto(String requestId, String eventId, String payload) {
        OutboxEventDto outboxEventDto = new OutboxEventDto();
        outboxEventDto.setRequestId(requestId);
        outboxEventDto.setEventId(eventId);
        outboxEventDto.setPayload(payload);
        outboxEventDto.setPartitionKey(requestId);
        outboxEventDto.setEventType("TRANSFER");
        outboxEventDto.setStatus("NEW");
        outboxEventDto.setTopicName(transferRequestedTopic.name());
        outboxEventDto.setAttemptCount(0);
        outboxEventDto.setCreatedAt(LocalDateTime.now());
        outboxEventDto.setUpdatedAt(LocalDateTime.now());
        return outboxEventDto;
    }
}
