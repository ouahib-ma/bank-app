package com.cacib.messageservice.exceptions;

public class MessageProcessingException extends RuntimeException {
    public MessageProcessingException(String message) {
        super(message);
    }

    public MessageProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
