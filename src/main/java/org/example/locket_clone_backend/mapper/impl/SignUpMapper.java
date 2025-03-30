package org.example.locket_clone_backend.mapper.impl;

import org.example.locket_clone_backend.domain.dto.SignUpReq;
import org.example.locket_clone_backend.domain.entity.UserEntity;
import org.example.locket_clone_backend.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class SignUpMapper implements Mapper<UserEntity, SignUpReq> {
    private final ModelMapper modelMapper;

    public SignUpMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    @Override
    public SignUpReq mapTo(UserEntity userEntity) {
        return modelMapper.map(userEntity, SignUpReq.class);
    }

    @Override
    public UserEntity mapFrom(SignUpReq signUpDto) {
        return modelMapper.map(signUpDto, UserEntity.class);
    }
}
