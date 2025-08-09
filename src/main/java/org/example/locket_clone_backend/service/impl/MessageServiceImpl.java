package org.example.locket_clone_backend.service.impl;

import java.security.MessageDigestSpi;
import java.util.List;
import java.util.stream.Collectors;

import org.example.locket_clone_backend.domain.dto.MessageResponse;
import org.example.locket_clone_backend.domain.dto.SentMessageDto;
import org.example.locket_clone_backend.domain.entity.message_entity.MessageEntity;
import org.example.locket_clone_backend.mapper.impl.MessageMapper;
import org.example.locket_clone_backend.repository.MessageRepository;
import org.example.locket_clone_backend.repository.UserRepository;
import org.example.locket_clone_backend.service.AuthService;
import org.example.locket_clone_backend.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
  private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
  private final MessageRepository messageRepository;
  private final AuthService authService;
  private final UserRepository userRepository;
  private final MessageMapper messageMapper;

  @Override
  public MessageResponse sendMessage(SentMessageDto messageDto, String senderEmail) {
    var sender = userRepository.findByEmail(senderEmail).get();
    var receiver = userRepository.findById(messageDto.getReceiverId()).get();
    var message = MessageEntity.builder()
        .sender(sender)
        .receiver(receiver)
        .text(messageDto.getText())
        .imageUrl(messageDto.getImageUrl())
        .build();
    var saved = messageRepository.save(message);
    System.out.print(saved.toString());
    var response = messageMapper.mapTo(saved);
    return response;
  }

  @Override
  public Page<MessageResponse> getMessages(Long receiverId, Pageable pageable) {
    var sender = authService.getCurrentUser();
    var receiver = userRepository.findById(receiverId);
    Page<MessageEntity> messageEntities = messageRepository.findMessagesBetweenUsers(sender.id, receiverId, pageable);
    Page<MessageResponse> responses = messageEntities.map(messageMapper::mapTo);
    return responses;

  }

}
