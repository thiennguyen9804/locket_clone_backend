package org.example.locket_clone_backend.mapper.impl;

import org.example.locket_clone_backend.domain.dto.InteractionDto;
import org.example.locket_clone_backend.domain.dto.UserDto;
import org.example.locket_clone_backend.domain.entity.InteractionEntity;
import org.example.locket_clone_backend.domain.entity.UserEntity;
import org.example.locket_clone_backend.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InteractionMapper implements Mapper<InteractionEntity, InteractionDto> {

  private final ModelMapper modelMapper;

  @Override
  public InteractionDto mapTo(InteractionEntity a) {
    return modelMapper.map(a, InteractionDto.class);
  }

  @Override
  public InteractionEntity mapFrom(InteractionDto interactionDto) {
    return modelMapper.map(interactionDto, InteractionEntity.class);
  }
}
