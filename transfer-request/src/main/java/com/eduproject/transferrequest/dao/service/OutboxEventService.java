package com.eduproject.transferrequest.dao.service;

import com.eduproject.transferrequest.dao.mapper.OutboxEventMapper;
import com.eduproject.transferrequest.dto.model.OutboxEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OutboxEventService {
    @Value("${currentSchema}")
    private String currentSchema;
    private final OutboxEventMapper outboxEventMapper;

    public void insert(OutboxEventDto dto) {
        outboxEventMapper.insert(currentSchema, dto);
    }

    public List<OutboxEventDto> findEventsToProcess(int toTake) {
       return outboxEventMapper.findEventsToProcess(currentSchema, toTake);
    }
    public void markAsSent(String eventId, LocalDateTime publishedAt, LocalDateTime updatedAt) {
        outboxEventMapper.markAsSent(currentSchema, eventId, publishedAt, updatedAt);
    }
}
