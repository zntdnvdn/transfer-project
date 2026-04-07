package com.eduproject.transferprocessing.service;

import com.eduproject.transferprocessing.dao.service.ProcessedEventService;
import com.eduproject.transferprocessing.dao.service.TransferProcessingResultService;
import com.eduproject.transferprocessing.dto.event.TransferRequestedEvent;
import com.eduproject.transferprocessing.dto.model.ProcessedEventDto;
import com.eduproject.transferprocessing.dto.model.TransferProcessingResultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransferProcessingService {
    private static final String CONSUMER_NAME = "transfer-processing-consumer";
    private final ProcessedEventService processedEventService;
    private final TransferProcessingResultService transferProcessingResultService;

    @Transactional
    public void process(TransferRequestedEvent event) {
        try {
            ProcessedEventDto processedEventDto = new ProcessedEventDto();
            processedEventDto.setEventId(event.getEventId());
            processedEventDto.setConsumerName(CONSUMER_NAME);
            processedEventDto.setProcessedAt(LocalDateTime.now());

            processedEventService.insert(processedEventDto);
        } catch (DuplicateKeyException ex) {
            return;
        }

        TransferProcessingResultDto resultDto = new TransferProcessingResultDto();
        resultDto.setRequestId(event.getRequestId());
        resultDto.setEventId(event.getEventId());
        resultDto.setProcessingStatus(resolveStatus(event));
        resultDto.setMessage(resolveFailureReason(event));
        resultDto.setProcessedAt(LocalDateTime.now());

        transferProcessingResultService.insert(resultDto);
        log.info("Successfully consumed event with eventId: {}", resultDto.getEventId());

    }

    private String resolveStatus(TransferRequestedEvent event) {
        if (event.getAmount() == null || event.getAmount().signum() <= 0) {
            return "FAILED";
        }
        if (event.getFromAccount() != null && event.getFromAccount().equals(event.getToAccount())) {
            return "FAILED";
        }
        return "COMPLETED";
    }

    private String resolveFailureReason(TransferRequestedEvent event) {
        if (event.getAmount() == null || event.getAmount().signum() <= 0) {
            return "AMOUNT_MUST_BE_POSITIVE";
        }
        if (event.getFromAccount() != null && event.getFromAccount().equals(event.getToAccount())) {
            return "SAME_ACCOUNT_TRANSFER_NOT_ALLOWED";
        }
        return null;
    }

}
