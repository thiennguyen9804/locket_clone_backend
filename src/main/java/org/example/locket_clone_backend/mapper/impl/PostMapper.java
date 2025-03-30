package org.example.locket_clone_backend.mapper.impl;

import org.example.locket_clone_backend.domain.dto.PostDto;
import org.example.locket_clone_backend.domain.entity.PostEntity;
import org.example.locket_clone_backend.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PostMapper implements Mapper<PostEntity, PostDto> {
	private final ModelMapper modelMapper;
	@Override
	public PostDto mapTo(PostEntity a) {
		return modelMapper.map(a, PostDto.class);		
	}

	@Override
	public PostEntity mapFrom(PostDto b) {
		return modelMapper.map(b, PostEntity.class);		
	}
	 
}
