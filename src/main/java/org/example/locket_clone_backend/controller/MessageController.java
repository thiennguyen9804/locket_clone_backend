package org.example.locket_clone_backend.controller;

import java.util.List;

import org.apache.logging.log4j.message.Message;
import org.example.locket_clone_backend.domain.dto.MessageResponse;
import org.example.locket_clone_backend.service.MessageService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

  @GetMapping("/messages/{id}")
  @ResponseStatus(value = HttpStatus.OK)
  public List<MessageResponse> getMessages(
      @PathVariable("id") Long receiverId,
      Pageable pageable) {
    return messageService.getMessages(receiverId, pageable);
  }

}
