package org.example.locket_clone_backend.controller;

import java.util.List;

import org.apache.logging.log4j.message.Message;
import org.example.locket_clone_backend.domain.dto.MessageResponse;
import org.example.locket_clone_backend.domain.dto.SentMessageDto;
import org.example.locket_clone_backend.service.AuthService;
import org.example.locket_clone_backend.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MessageController {
  private final MessageService messageService;
  private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

  @GetMapping("/messages/{receiverId}")
  @ResponseStatus(value = HttpStatus.OK)
  public Page<MessageResponse> getMessages(
      @PathVariable("receiverId") Long receiverId,
      Pageable pageable) {

    // logger.info("receiverId: " + receiverId);
    // logger.info("pageable: " + pageable);

    return messageService.getMessages(receiverId, pageable);
  }

  // @GetMapping("/messages")
  // @ResponseStatus(value = HttpStatus.OK)
  // public List<MessageResponse> getMessageList() {
  // var userEntity = authService.getCurrentUser();
  //
  // }

}
