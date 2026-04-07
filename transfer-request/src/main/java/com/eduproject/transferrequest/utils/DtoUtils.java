package com.eduproject.transferrequest.utils;

import com.eduproject.transferrequest.dto.model.OutboxEventDto;
import com.eduproject.transferrequest.dto.model.TransferRequestDto;
import com.eduproject.transferrequest.dto.rq.CreateTransferRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DtoUtils {
    public static TransferRequestDto createTransferRequestDto(CreateTransferRequest request) {
        TransferRequestDto transferRequestDto = new TransferRequestDto();
        transferRequestDto.setRequestId(request.getRequestId());
        transferRequestDto.setAmount(request.getAmount());
        transferRequestDto.setCurrency(request.getCurrency());
        transferRequestDto.setFromAccount(request.getFromAccount());
        transferRequestDto.setToAccount(request.getToAccount());
        transferRequestDto.setStatus("NEW");
        transferRequestDto.setCreatedAt(LocalDateTime.now());
        transferRequestDto.setUpdatedAt(LocalDateTime.now());
        return transferRequestDto;
    }

}
