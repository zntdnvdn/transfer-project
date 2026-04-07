package com.eduproject.transferprocessing.dto.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransferProcessingResultDto {
    private String requestId;
    private String eventId;
    private String processingStatus;
    private String message;
    private LocalDateTime processedAt;
}
