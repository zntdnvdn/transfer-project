package com.eduproject.transferrequest.service;

import com.eduproject.transferrequest.dao.service.OutboxEventService;
import com.eduproject.transferrequest.dto.model.OutboxEventDto;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventSendingProcessor {
    private final EventPublisherService eventPublisherService;
    private final OutboxEventService outboxEventService;
    @Value("${limit.event-processor}")
    private int toTake;

    @Transactional
    @Scheduled(initialDelayString = "${scheduler.eventSending.initialDelayInitialSeconds}",
    fixedDelayString= "${scheduler.eventSending.fixedDelayInitialSeconds}", scheduler = "eventSendingScheduler")
    public void process() {
        log.info("Check events to publish");
        List<OutboxEventDto> events = outboxEventService.findEventsToProcess(toTake);
        if (events.isEmpty()) {
            log.info("No events to publish found");
            return;
        }
        eventPublisherService.publishNextEvent(events);
        
    }
}
