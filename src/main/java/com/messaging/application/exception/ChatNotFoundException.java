package com.messaging.application.exception;

public class ChatNotFoundException extends RuntimeException {

    public ChatNotFoundException(String chatId) {
        super("Chat not found with id: " + chatId);
    }
}
