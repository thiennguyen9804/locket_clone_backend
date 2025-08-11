package org.example.locket_clone_backend.controller;

import java.util.List;

import org.example.locket_clone_backend.domain.dto.MessageResponse;
import org.example.locket_clone_backend.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MessageController {
  private final MessageService messageService;
  private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

  @GetMapping("/messages/{receiverId}")
  @Operation(summary = "Get all messages with <receiver>", security = @SecurityRequirement(name = "bearerAuth"))
  @ResponseStatus(value = HttpStatus.OK)
  public Page<MessageResponse> getMessagesWithReceiver(
      @PathVariable("receiverId") Long receiverId,
      Pageable pageable) {
    return messageService.getMessagesWithReceiver(receiverId, pageable);
  }

  @GetMapping("/messages")
  @Operation(summary = "Get all friend, and their latest message", security = @SecurityRequirement(name = "bearerAuth"))
  @ResponseStatus(value = HttpStatus.OK)
  public List<MessageResponse> getMessages() {
    return messageService.getMessages();
  }

}
