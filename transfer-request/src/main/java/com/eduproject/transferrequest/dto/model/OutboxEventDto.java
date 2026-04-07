package com.eduproject.transferrequest.dto.model;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class OutboxEventDto {
    private String requestId;
    private String eventId;
    private String eventType;
    private String topicName;
    private String partitionKey;
    private String payload;
    private String status;
    private Integer attemptCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime publishedAt;
}
