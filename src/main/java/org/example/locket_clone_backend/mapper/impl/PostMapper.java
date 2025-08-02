package org.example.locket_clone_backend.mapper.impl;

import java.util.HashSet;
import java.util.stream.Collectors;

import org.example.locket_clone_backend.domain.dto.PostDto;
import org.example.locket_clone_backend.domain.dto.UserDto;
import org.example.locket_clone_backend.domain.entity.PostEntity;
import org.example.locket_clone_backend.domain.entity.UserEntity;
import org.example.locket_clone_backend.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PostMapper implements Mapper<PostEntity, PostDto> {
  private final ModelMapper modelMapper;
  private final Mapper<UserEntity, UserDto> userMapper;
  private final InteractionMapper interactionMapper;

  @Override
  public PostDto mapTo(PostEntity a) {
    PostDto postDto = new PostDto();
    postDto.setId(a.getId());
    postDto.setImageUrl(a.getImageUrl());
    postDto.setCaption(a.getCaption());
    postDto.setCreatedAt(a.getCreatedAt());
    postDto.setUser(userMapper.mapTo(a.getUser()));
    if (a.getInteractionList() != null) {
      postDto.setInteractionList(
          a.getInteractionList().stream()
              .map(interactionMapper::mapTo)
              .collect(Collectors.toSet()));
    } else {
      postDto.setInteractionList(new HashSet<>());
    }
    return postDto;
  }

  @Override
  public PostEntity mapFrom(PostDto b) {
    return modelMapper.map(b, PostEntity.class);
  }

}
