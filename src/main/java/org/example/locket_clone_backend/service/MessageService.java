package org.example.locket_clone_backend.service;

import java.util.List;

import org.example.locket_clone_backend.domain.dto.MessageResponse;
import org.example.locket_clone_backend.domain.dto.SentMessageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MessageService {
  public MessageResponse sendMessage(SentMessageDto messageDto, String senderEmail);

  public Page<MessageResponse> getMessagesWithReceiver(Long receiverId, Pageable pageable);

  public List<MessageResponse> getMessages();
}
