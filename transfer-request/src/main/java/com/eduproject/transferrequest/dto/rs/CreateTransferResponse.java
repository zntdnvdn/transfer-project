package com.eduproject.transferrequest.dto.rs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTransferResponse {
    private String requestId;
    private String status;
}
