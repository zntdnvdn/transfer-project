package com.eduproject.transferprocessing.dto.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProcessedEventDto {
    private String eventId;
    private String consumerName;
    private LocalDateTime processedAt;

}
