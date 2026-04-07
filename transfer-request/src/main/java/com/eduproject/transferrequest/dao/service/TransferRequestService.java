package com.eduproject.transferrequest.dao.service;

import com.eduproject.transferrequest.dao.mapper.TransferRequestMapper;
import com.eduproject.transferrequest.dto.model.TransferRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransferRequestService {
    @Value("${currentSchema}")
    private String currentSchema;
    private final TransferRequestMapper transferRequestMapper;

    public void insert(TransferRequestDto dto) {
        transferRequestMapper.insert(currentSchema, dto);
    }
}
