package com.eduproject.transferrequest.controller;

import com.eduproject.transferrequest.dto.rq.CreateTransferRequest;
import com.eduproject.transferrequest.dto.rs.CreateTransferResponse;
import com.eduproject.transferrequest.dto.rs.TransferResponse;
import com.eduproject.transferrequest.service.TransferRequestProcessor;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/transfers")
public class TransferRequestController {
    private final TransferRequestProcessor transferRequestProcessor;

    @PostMapping("/create")
    public ResponseEntity<CreateTransferResponse> createTransfer(
            @Valid @RequestBody CreateTransferRequest request
    ) {
        log.info("Got request requestId: {} ", request.getRequestId());
        CreateTransferResponse response = transferRequestProcessor.createTransfer(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<TransferResponse> getTransfer(@PathVariable String requestId) {
        TransferResponse response = transferRequestProcessor.getByRequestId(requestId);
        return ResponseEntity.ok(response);
    }
}

