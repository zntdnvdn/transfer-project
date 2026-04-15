package com.eduproject.transferrequest.advice;

import com.eduproject.transferrequest.exceptions.TransferRequestNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(TransferRequestNotFoundException.class)
    public ProblemDetail handleTransferRequestNotFound(TransferRequestNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Transfer request not found");
        problemDetail.setDetail(ex.getMessage());
        return problemDetail;
    }
}
