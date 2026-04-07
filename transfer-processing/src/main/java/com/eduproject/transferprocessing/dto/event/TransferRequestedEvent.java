package com.eduproject.transferprocessing.dto.event;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransferRequestedEvent {
    private String eventId;
    private String requestId;
    private String fromAccount;
    private String toAccount;
    private BigDecimal amount;
    private String currency;
    private LocalDateTime createdAt;
}
