package com.eduproject.transferrequest.service;

import com.eduproject.transferrequest.dao.service.OutboxEventService;
import com.eduproject.transferrequest.dao.service.TransferRequestService;
import com.eduproject.transferrequest.dto.event.TransferRequestedEvent;
import com.eduproject.transferrequest.dto.model.OutboxEventDto;
import com.eduproject.transferrequest.dto.model.TransferRequestDto;
import com.eduproject.transferrequest.dto.rq.CreateTransferRequest;
import com.eduproject.transferrequest.dto.rs.CreateTransferResponse;
import com.eduproject.transferrequest.dto.rs.TransferResponse;
import com.eduproject.transferrequest.exceptions.TransferRequestNotFoundException;
import com.eduproject.transferrequest.service.factory.OutboxEventFactory;
import com.eduproject.transferrequest.service.factory.TransferEventFactory;
import com.eduproject.transferrequest.utils.DtoUtils;
import com.eduproject.transferrequest.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransferRequestProcessor {
    private final TransferRequestService transferRequestService;
    private final OutboxEventService outboxEventService;
//    private final TransferValidator transferValidator;
    private final TransferEventFactory transferEventFactory;
    private final OutboxEventFactory outboxEventFactory;

    @Transactional
    public CreateTransferResponse createTransfer(CreateTransferRequest request) {
//        transferValidator.validateForCreate(request);

        TransferRequestDto transferRequestDto = DtoUtils.createTransferRequestDto(request);
        transferRequestService.insert(transferRequestDto);

        TransferRequestedEvent event = transferEventFactory.buildTransferRequestedEvent(transferRequestDto);
        String payload = Utils.toJson(event);

        OutboxEventDto outboxEventDto = outboxEventFactory.createOutboxEventDto(
                transferRequestDto.getRequestId(),
                event.getEventId(),
                payload
        );
        outboxEventService.insert(outboxEventDto);
        log.info("Created event: eventId: {}, requestId: {}", transferRequestDto.getRequestId(),
                event.getEventId());

        return new CreateTransferResponse(
                transferRequestDto.getRequestId(),
                transferRequestDto.getStatus()
        );
    }

    @Transactional(readOnly = true)
    public TransferResponse getByRequestId(String requestId) {
        log.info("Get transfer by requestId={}", requestId);

        TransferRequestDto transferRequestDto = transferRequestService.getByRequestId(requestId)
                .orElseThrow(() -> new TransferRequestNotFoundException(requestId));

        return new TransferResponse(
                transferRequestDto.getRequestId(),
                transferRequestDto.getStatus()
        );
    }
}
