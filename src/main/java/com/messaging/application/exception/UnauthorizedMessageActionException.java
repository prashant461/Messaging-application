package com.messaging.application.exception;

public class UnauthorizedMessageActionException extends RuntimeException {
    public UnauthorizedMessageActionException(String msg) {
        super(msg);
    }
}
