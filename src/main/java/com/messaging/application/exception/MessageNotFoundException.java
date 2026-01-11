package com.messaging.application.exception;

public class MessageNotFoundException extends RuntimeException {
    public MessageNotFoundException(String msg) {
        super(msg);
    }
}
