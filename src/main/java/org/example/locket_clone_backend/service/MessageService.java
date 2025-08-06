package org.example.locket_clone_backend.service;

import org.example.locket_clone_backend.domain.dto.MessageResponse;
import org.example.locket_clone_backend.domain.dto.SentMessageDto;

public interface MessageService {
  public MessageResponse sendMessage(SentMessageDto messageDto);
}
