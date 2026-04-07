package com.eduproject.transferprocessing.consumer;

import com.eduproject.transferprocessing.dto.event.TransferRequestedEvent;
import com.eduproject.transferprocessing.service.TransferProcessingService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class TransferRequestedConsumer {
    private final TransferProcessingService transferProcessingService;

    public TransferRequestedConsumer(TransferProcessingService transferProcessingService) {
        this.transferProcessingService = transferProcessingService;
    }

    @KafkaListener(
            topics = "${app.kafka.topic.transfer-requested}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(TransferRequestedEvent event, Acknowledgment acknowledgment) {
        transferProcessingService.process(event);
        acknowledgment.acknowledge();
    }

}
