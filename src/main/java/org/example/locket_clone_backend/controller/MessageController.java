package org.example.locket_clone_backend.controller;

import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;

import org.example.locket_clone_backend.domain.dto.SentMessageDto;
import org.example.locket_clone_backend.service.MessageService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Controller
@RequiredArgsConstructor
public class MessageController {
  private final SimpMessagingTemplate simpMessagingTemplate;
  private final MessageService messageService;

  @MessageMapping("/app/chat.add-message")
  public void sendMessage(
      @Payload SentMessageDto messageDto) {
    var response = messageService.sendMessage(messageDto);
    simpMessagingTemplate.convertAndSendToUser(
        response.getReceiver().id.toString(),
        "/secured/user/queue/specific-user",
        response);
  }

}
