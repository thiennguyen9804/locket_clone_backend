package org.example.locket_clone_backend.service;

import java.util.List;

import org.example.locket_clone_backend.domain.dto.MessageResponse;
import org.example.locket_clone_backend.domain.dto.SentMessageDto;
import org.springframework.data.domain.Pageable;

public interface MessageService {
  public MessageResponse sendMessage(SentMessageDto messageDto);

  public List<MessageResponse> getMessages(Long receiverId, Pageable pageable);
}
