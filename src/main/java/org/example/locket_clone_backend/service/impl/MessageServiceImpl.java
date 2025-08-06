package org.example.locket_clone_backend.service.impl;

import org.example.locket_clone_backend.domain.dto.MessageResponse;
import org.example.locket_clone_backend.domain.dto.SentMessageDto;
import org.example.locket_clone_backend.domain.entity.message_entity.MessageEntity;
import org.example.locket_clone_backend.domain.entity.message_entity.MessageId;
import org.example.locket_clone_backend.mapper.impl.MessageMapper;
import org.example.locket_clone_backend.repository.MessageRepository;
import org.example.locket_clone_backend.repository.UserRepository;
import org.example.locket_clone_backend.service.AuthService;
import org.example.locket_clone_backend.service.MessageService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

  private final MessageRepository messageRepository;
  private final AuthService authService;
  private final UserRepository userRepository;
  private final MessageMapper messageMapper;

  @Override
  public MessageResponse sendMessage(SentMessageDto messageDto) {
    var sender = authService.getCurrentUser();
    var receiver = userRepository.findById(messageDto.getReceiverId()).get();
    var message = MessageEntity.builder()
        .id(MessageId.builder().senderId(sender.id).receiverId(receiver.id).build())
        .sender(sender)
        .receiver(receiver)
        .text(messageDto.getText())
        .imageUrl(messageDto.getImageUrl())
        .build();
    var saved = messageRepository.save(message);
    var response = messageMapper.mapTo(saved);
    return response;
  }

}
