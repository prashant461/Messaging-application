package com.messaging.application.message;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
@Tag(name = "Messages")
public class MessageController {

    private final MessageService messageService;
    
    @MessageMapping("/chat.send")
    public void sendText(MessageRequest request) {
        messageService.sendTextMessage(request);
    }

    @PatchMapping("/seen")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void markSeen(@RequestParam String chatId,
                         @RequestParam String receiverId) {
        messageService.markMessagesAsSeen(chatId, receiverId);
    }

    @GetMapping("/{chatId}")
    public Page<MessageResponse> getMessages(
            @PathVariable String chatId,
            Pageable pageable
    ) {
        return messageService.getChatMessages(chatId, pageable);
    }
}

