package com.eduproject.transferrequest.service.factory;

import com.eduproject.transferrequest.dto.event.TransferRequestedEvent;
import com.eduproject.transferrequest.dto.model.TransferRequestDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;
@Component
public class TransferEventFactory {
    public TransferRequestedEvent buildTransferRequestedEvent(TransferRequestDto transferRequestDto) {
        TransferRequestedEvent event = new TransferRequestedEvent();
        event.setEventId(UUID.randomUUID().toString());
        event.setRequestId(transferRequestDto.getRequestId());
        event.setFromAccount(transferRequestDto.getFromAccount());
        event.setToAccount(transferRequestDto.getToAccount());
        event.setAmount(transferRequestDto.getAmount());
        event.setCurrency(transferRequestDto.getCurrency());
        event.setCreatedAt(LocalDateTime.now());
        return event;
    }
}
