package com.eduproject.transferrequest.dao.service;

import com.eduproject.transferrequest.dao.mapper.TransferRequestMapper;
import com.eduproject.transferrequest.dto.model.TransferRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransferRequestService {
    @Value("${currentSchema}")
    private String currentSchema;
    private final TransferRequestMapper transferRequestMapper;

    public void insert(TransferRequestDto dto) {
        transferRequestMapper.insert(currentSchema, dto);
    }

    public Optional<TransferRequestDto> getByRequestId(String requestId) {
        log.info("Find transfer request by requestId={}", requestId);
        return Optional.ofNullable(transferRequestMapper.findByRequestId(currentSchema, requestId));
    }
}
