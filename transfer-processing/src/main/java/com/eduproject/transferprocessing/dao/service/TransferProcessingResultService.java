package com.eduproject.transferprocessing.dao.service;

import com.eduproject.transferprocessing.dao.mapper.TransferProcessingResultMapper;
import com.eduproject.transferprocessing.dto.model.ProcessedEventDto;
import com.eduproject.transferprocessing.dto.model.TransferProcessingResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransferProcessingResultService {
    private final TransferProcessingResultMapper transferProcessingResultMapper;
    @Value("${currentSchema}")
    private String currentSchema;

    public void insert(TransferProcessingResultDto dto) {
        transferProcessingResultMapper.insert(currentSchema, dto);
    }
}
