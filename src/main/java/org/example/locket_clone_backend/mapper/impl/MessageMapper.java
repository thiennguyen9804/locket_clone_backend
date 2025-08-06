package org.example.locket_clone_backend.mapper.impl;

import org.example.locket_clone_backend.domain.dto.InteractionDto;
import org.example.locket_clone_backend.domain.dto.MessageResponse;
import org.example.locket_clone_backend.domain.entity.InteractionEntity;
import org.example.locket_clone_backend.domain.entity.message_entity.MessageEntity;
import org.example.locket_clone_backend.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MessageMapper implements Mapper<MessageEntity, MessageResponse> {

  private final ModelMapper modelMapper;

  @Override
  public MessageResponse mapTo(MessageEntity a) {
    return modelMapper.map(a, MessageResponse.class);
  }

  @Override
  public MessageEntity mapFrom(MessageResponse b) {
    return modelMapper.map(b, MessageEntity.class);
  }

}
