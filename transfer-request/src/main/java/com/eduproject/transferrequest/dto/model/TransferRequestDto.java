package com.eduproject.transferrequest.dto.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransferRequestDto {
    private String requestId;
    private String fromAccount;
    private String toAccount;
    private BigDecimal amount;
    private String currency;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
