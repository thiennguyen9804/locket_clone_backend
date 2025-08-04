package org.example.locket_clone_backend.service;

import org.example.locket_clone_backend.domain.dto.SignInReq;
import org.example.locket_clone_backend.domain.dto.SignInRes;
import org.example.locket_clone_backend.domain.dto.SignUpReq;
import org.example.locket_clone_backend.domain.dto.UserDto;
import org.example.locket_clone_backend.domain.entity.UserEntity;

public interface AuthService {
  SignInRes signIn(SignInReq signInReq) throws RuntimeException;

  UserDto signUp(SignUpReq signUpDto) throws RuntimeException;

  UserEntity getCurrentUser();
}
