package com.messaging.application.chat;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/chats")
@RequiredArgsConstructor
@Tag(name = "Chats")
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    public ResponseEntity<ChatIdResponse> createChat(
            @RequestParam("senderId") String senderId,
            @RequestParam("receiverId") String receiverId
    ) {
        return ResponseEntity.ok(
                new ChatIdResponse(chatService.createChat(senderId, receiverId))
        );
    }

    @GetMapping
    public ResponseEntity<Page<ChatResponse>> getUserChats(
            @RequestParam("userId") String userId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(chatService.getUserChats(userId, pageable));
    }

    @DeleteMapping("/{chatId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteChatForUser(
            @PathVariable String chatId,
            @RequestParam("userId") String userId
    ) {
        chatService.deleteChatForUser(chatId, userId);
    }
}

