package com.eduproject.transferrequest.exceptions;

public class TransferRequestNotFoundException extends RuntimeException {
    public TransferRequestNotFoundException(String requestId) {
        super("Transfer request not found. requestId=" + requestId);
    }
}
