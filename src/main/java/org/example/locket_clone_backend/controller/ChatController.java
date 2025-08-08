package org.example.locket_clone_backend.controller;

import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;

import java.security.Principal;

import org.example.locket_clone_backend.domain.dto.SentMessageDto;
import org.example.locket_clone_backend.domain.entity.UserEntity;
import org.example.locket_clone_backend.service.MessageService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Controller
@RequiredArgsConstructor
public class ChatController {
  private final SimpMessagingTemplate simpMessagingTemplate;
  private final MessageService messageService;

  @MessageMapping("/chat.add-message")
  public void sendMessage(
      @Payload SentMessageDto messageDto,
      Principal principal) {
    String senderEmail = principal.getName();
    var response = messageService.sendMessage(messageDto, senderEmail);
    simpMessagingTemplate.convertAndSendToUser(
        response.getReceiver().id.toString(),
        "/queue/messages",
        response);
  }

}
