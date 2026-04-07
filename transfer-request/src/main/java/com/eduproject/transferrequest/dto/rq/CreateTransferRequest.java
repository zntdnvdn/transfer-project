package com.eduproject.transferrequest.dto.rq;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTransferRequest {
    @NotBlank
    private String requestId;

    @NotBlank
    private String fromAccount;

    @NotBlank
    private String toAccount;

    @NotNull
    private BigDecimal amount;

    @NotBlank
    private String currency;
}
