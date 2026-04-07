package com.eduproject.transferprocessing.dao.service;

import com.eduproject.transferprocessing.dao.mapper.ProcessedEventMapper;
import com.eduproject.transferprocessing.dto.model.ProcessedEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProcessedEventService {
    private final ProcessedEventMapper processedEventMapper;
    @Value("${currentSchema}")
    private String currentSchema;

    public void insert(ProcessedEventDto dto) {
        processedEventMapper.insert(currentSchema, dto);
    }

}
